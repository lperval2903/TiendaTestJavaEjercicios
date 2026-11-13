-- Usuario inicial para acceder al mantenimiento de usuarios.
-- Credenciales: admin / admin123
INSERT INTO usuario (usuario, password, rol)
SELECT 'admin',
       '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9',
       'administrador'
WHERE NOT EXISTS (
    SELECT 1 FROM usuario WHERE usuario = 'admin'
);
