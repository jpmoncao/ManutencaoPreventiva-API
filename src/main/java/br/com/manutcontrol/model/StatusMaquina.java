package br.com.manutcontrol.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Situação operacional da máquina")
public enum StatusMaquina {
    ATIVA, EM_MANUTENCAO, INATIVA
}
