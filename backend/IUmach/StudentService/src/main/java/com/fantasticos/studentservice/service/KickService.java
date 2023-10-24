package com.fantasticos.studentservice.service;

import com.fantasticos.studentservice.dto.KickDTO;

public interface KickService {

	String nuevaVotacion(KickDTO modelo, String userName, String bearerToken);

	String votar(KickDTO modelo, String userName, String bearerToken);

	String rechazar(KickDTO modelo, String userName);

}
