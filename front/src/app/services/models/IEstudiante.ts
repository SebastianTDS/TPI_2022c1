import { ICarrera } from "./ICarrera";
import { IFacultad } from "./IFacultad";
import { IMateria } from "./IMateria";
import { ITag } from "./ITag";

export interface IEstudiante{
  id?: number;
  parKey?: string;
  puntos?: number;
  materiasDia?: IMateria[];
  materiasTarde?: IMateria[];
  materiasNoce?: IMateria[];
  carreras?: ICarrera[];
  facultad?: IFacultad;
  intereses?: ITag[];
  

  [key: string]: any;
}