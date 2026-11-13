CREATE TABLE IF NOT EXISTS usuario (
    idUsuario INT NOT NULL AUTO_INCREMENT,
    usuario VARCHAR(45) NOT NULL,
    password VARCHAR(128) NOT NULL,
    rol VARCHAR(24) NOT NULL,
    PRIMARY KEY (idUsuario),
    CONSTRAINT chk_usuario_rol
        CHECK (rol IN ('cliente', 'vendedor', 'administrador'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
