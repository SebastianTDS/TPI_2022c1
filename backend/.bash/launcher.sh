#!/bin/sh
# This is a comment!

RUTA=${PWD#*/}
CARPETA=${PWD##*/}
DIR="/"${RUTA/"$CARPETA"/""}"IUmach/"

GREEN="\033[0;32m"
CIAN="\033[0;36m"
PURPLE="\033[0;35m"
NC="\033[0m"

BANNER="  ${CIAN}▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄${NC}

  ${PURPLE}██╗██╗   ██╗███╗   ███╗ █████╗  ██████╗██╗  ██╗${GREEN}   ███████╗██╗  ██╗${NC}
  ${PURPLE}██║██║   ██║████╗ ████║██╔══██╗██╔════╝██║  ██║${GREEN}   ██╔════╝██║  ██║${NC}
  ${PURPLE}██║██║   ██║██╔████╔██║███████║██║     ███████║${GREEN}   ███████╗███████║${NC}
  ${PURPLE}██║██║   ██║██║╚██╔╝██║██╔══██║██║     ██╔══██║${GREEN}   ╚════██║██╔══██║${NC}
  ${PURPLE}██║╚██████╔╝██║ ╚═╝ ██║██║  ██║╚██████╗██║  ██║${GREEN}██╗███████║██║  ██║${NC}
  ${PURPLE}╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝${GREEN}╚═╝╚══════╝╚═╝  ╚═╝${NC}
  ${CIAN}▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄${NC}\n"


mainmenu() {
  clear

  echo -ne "${BANNER}
  ${CIAN}╔════════════════════════════════════════════════════════════════╗
  ${CIAN}║                        Menu Principal                          ║
  ${CIAN}╠════════════════════════════════════════════════════════════════╣
  ${CIAN}║ ${GREEN}1. Lanzar aplicación${CIAN}                                           ║
  ${CIAN}║ ${GREEN}2. Listar micro-servicios${CIAN}                                      ║
  ${CIAN}║ ${GREEN}0. Salir${CIAN}                                                       ║
  ${CIAN}╚════════════════════════════════════════════════════════════════╝
  ${GREEN}${1}
  ${PURPLE}> Seleccione una opción: "
  read -r ans
    
  echo -e ${NC}
   
    case $ans in
    1)
        lanzarApp
        ;;
    2)
        listarMicros
        mainmenu
        ;;
    0)
        clear
        exit 0
        ;;
    *)
        mainmenu "\n  Opción no valida intente nuevamente...\n"
        ;;
    esac
}

listarMicros(){
  clear

  INDEX=1
  LIST=""

  echo -ne "${BANNER}
  ${CIAN}╔════════════════════════════════════════════════════════════════╗
  ${CIAN}║                  Microservicios Disponibles                    ║
  ${CIAN}╠════════════════════════════════════════════════════════════════╣\n"
  
  for i in "$DIR"*/
  do
  	i=${i/$DIR/""}; i=${i/"/"/""}
  
  	if [[ $i == *"Service" ]] && [[ $i != "GatewayService" ]]; then
  	
  		if [[ -z $LIST ]]; then 
	  		LIST=$i
	  	else
	  		LIST=$LIST"\n"$i
	  	fi
  	
  		OPTION="${INDEX}. ${i}"
  		LEN=$((62-${#OPTION}))
  		
  		echo -ne "  ${CIAN}║ ${GREEN}${OPTION/"Service"/" Service"}${CIAN}" 
  		for i in $(seq 1 $LEN); do echo -ne " "; done
  		echo -ne "║\n"
  		
  		INDEX=$(($INDEX+1))
  	fi
  done
  
  
  echo -ne "  ${CIAN}║ ${GREEN}0. Volver${CIAN}                                                      ║
  ${CIAN}╚════════════════════════════════════════════════════════════════╝
  ${GREEN}${1}
  ${PURPLE}> Seleccione una opción: "
  
  LIST=($(echo -e $LIST))

  read -r ans
  
  echo -e ${NC}
  
  case $ans in
  0)
      mainmenu
      ;;
  *)
     for ((i = 0; i < ${INDEX}; i++)); do
        if [[ $i = $ans ]]; then
           lanzarServ ${LIST[$(($i-1))]}
        fi
     done
     listarMicros "\n  Opción no valida intente nuevamente...\n"
     ;;
  esac
}

lanzarServ(){
	gnome-terminal --window --maximize --working-directory=$DIR -- sh -c '
		rdom () { local IFS=\> ; read -d \< E C ;}

		SN=""
		SV=""

		while rdom; do
    			if [[ $E = "artifactId" ]] && [[ $C != "IUmach" ]]; then
				SN=${C/Service/" Service"}
			elif [[ $E = "version" ]]; then
				SV=$C
			elif [[ ! -z $SN ]] && [[ ! -z $SV ]]; then
				break
			fi
		done < "${2}/pom.xml"

		if [[ $2 == *"Service" ]]; then
			gnome-terminal --tab --title="$SN $SV" --working-directory="$1$2/" -- mvn spring-boot:run -Dspring-boot.run.arguments="--COGNITO-POOL=https://cognito-idp.us-east-1.amazonaws.com/us-east-1_YuP95O39S --COGNITO-JWT=https://cognito-idp.us-east-1.amazonaws.com/us-east-1_YuP95O39S/.well-known/jwks.json --SERVICE_USER=root --SERVICE_PASSWORD=root --ID_ACCESS_KEY=AKIAWZF2W6U2TKCFIVIP --SECRET_ACCESS_KEY=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 --REGION=us-east-1 --BUCKET_NAME=umatchfiles"
			gnome-terminal --tab --title="Gateway Service $SV" --working-directory="${1}GatewayService/" -- mvn spring-boot:run
		fi
	' sh "$DIR" "$1"
}

lanzarApp(){
	gnome-terminal --window --maximize --working-directory=$DIR -- sh -c '
		rdom () { local IFS=\> ; read -d \< E C ;}

		for i in */
		do
			SN=""
			SV=""

			while rdom; do
	    			if [[ $E = "artifactId" ]] && [[ $C != "IUmach" ]]; then
					SN=${C/Service/" Service"}
				elif [[ $E = "version" ]]; then
					SV=$C
				elif [[ ! -z $SN ]] && [[ ! -z $SV ]]; then
					break
				fi
			done < "${i}pom.xml"

			if [[ $i == *"Service/" ]]; then
				gnome-terminal --tab --title="$SN $SV" --working-directory="$1$i" -- mvn spring-boot:run -Dspring-boot.run.arguments="--COGNITO-POOL=https://cognito-idp.us-east-1.amazonaws.com/us-east-1_YuP95O39S --COGNITO-JWT=https://cognito-idp.us-east-1.amazonaws.com/us-east-1_YuP95O39S/.well-known/jwks.json --SERVICE_USER=root --SERVICE_PASSWORD=root --ID_ACCESS_KEY=AKIAWZF2W6U2TKCFIVIP --SECRET_ACCESS_KEY=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 --REGION=us-east-1 --BUCKET_NAME=umatchfiles"
			fi
		done
	' sh "$DIR"
}

mainmenu

