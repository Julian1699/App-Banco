-- Insertar la categoría "Roles y Permisos"
INSERT INTO listas (nombre, descripcion, habilitado) 
VALUES 
('Roles y Permisos', 'Categoría', TRUE);

-- Insertar módulos dentro de la categoría "Roles y Permisos"
INSERT INTO valores_listas (lista_id, valor, descripcion, habilitado, orden)
VALUES
((SELECT id FROM listas WHERE descripcion = 'Categoría' AND nombre = 'Roles y Permisos' LIMIT 1), 'Roles', 'Módulo para la gestión de roles', TRUE, 1),
((SELECT id FROM listas WHERE descripcion = 'Categoría' AND nombre = 'Roles y Permisos' LIMIT 1), 'Permisos a Roles', 'Módulo para asignar permisos a roles', TRUE, 2),
((SELECT id FROM listas WHERE descripcion = 'Categoría' AND nombre = 'Roles y Permisos' LIMIT 1), 'Usuarios', 'Módulo para la gestión de usuarios', TRUE, 3);

INSERT INTO paises (nombre, codigo, habilitado, created_by) VALUES
('Argentina', 'ARG', TRUE, 'admin'),
('Bolivia', 'BOL', TRUE, 'admin'),
('Brasil', 'BRA', TRUE, 'admin'),
('Canadá', 'CAN', TRUE, 'admin'),
('Chile', 'CHL', TRUE, 'admin'),
('Colombia', 'COL', TRUE, 'admin'),
('Costa Rica', 'CRI', TRUE, 'admin'),
('Cuba', 'CUB', TRUE, 'admin'),
('Ecuador', 'ECU', TRUE, 'admin'),
('El Salvador', 'SLV', TRUE, 'admin'),
('España', 'ESP', TRUE, 'admin'),
('Estados Unidos', 'USA', TRUE, 'admin'),
('Guatemala', 'GTM', TRUE, 'admin'),
('Honduras', 'HND', TRUE, 'admin'),
('México', 'MEX', TRUE, 'admin'),
('Nicaragua', 'NIC', TRUE, 'admin'),
('Panamá', 'PAN', TRUE, 'admin'),
('Paraguay', 'PRY', TRUE, 'admin'),
('Perú', 'PER', TRUE, 'admin'),
('Uruguay', 'URY', TRUE, 'admin');

INSERT INTO departamentos (nombre, pais_id, codigo, habilitado, created_by) VALUES
('Amazonas', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'AMA', TRUE, 'admin'),
('Antioquia', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'ANT', TRUE, 'admin'),
('Arauca', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'ARA', TRUE, 'admin'),
('Atlántico', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'ATL', TRUE, 'admin'),
('Bolívar', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'BOL', TRUE, 'admin'),
('Boyacá', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'BOY', TRUE, 'admin'),
('Caldas', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'CAL', TRUE, 'admin'),
('Caquetá', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'CAQ', TRUE, 'admin'),
('Casanare', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'CAS', TRUE, 'admin'),
('Cauca', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'CAU', TRUE, 'admin'),
('Cesar', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'CES', TRUE, 'admin'),
('Chocó', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'CHO', TRUE, 'admin'),
('Córdoba', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'COR', TRUE, 'admin'),
('Cundinamarca', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'CUN', TRUE, 'admin'),
('Guainía', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'GUA', TRUE, 'admin'),
('Guaviare', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'GUV', TRUE, 'admin'),
('Huila', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'HUI', TRUE, 'admin'),
('La Guajira', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'LAG', TRUE, 'admin'),
('Magdalena', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'MAG', TRUE, 'admin'),
('Meta', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'MET', TRUE, 'admin'),
('Nariño', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'NAR', TRUE, 'admin'),
('Norte de Santander', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'NSA', TRUE, 'admin'),
('Putumayo', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'PUT', TRUE, 'admin'),
('Quindío', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'QUI', TRUE, 'admin'),
('Risaralda', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'RIS', TRUE, 'admin'),
('San Andrés y Providencia', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'SAP', TRUE, 'admin'),
('Santander', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'SAN', TRUE, 'admin'),
('Sucre', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'SUC', TRUE, 'admin'),
('Tolima', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'TOL', TRUE, 'admin'),
('Valle del Cauca', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'VAC', TRUE, 'admin'),
('Vaupés', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'VAU', TRUE, 'admin'),
('Vichada', (SELECT id FROM paises WHERE nombre = 'Colombia'), 'VIC', TRUE, 'admin');

INSERT INTO ciudades (nombre, departamento_id, codigo, habilitado, created_by) VALUES
('Leticia', (SELECT id FROM departamentos WHERE nombre = 'Amazonas'), 'LET', TRUE, 'admin'),
('Puerto Nariño', (SELECT id FROM departamentos WHERE nombre = 'Amazonas'), 'PNA', TRUE, 'admin'),
('Medellín', (SELECT id FROM departamentos WHERE nombre = 'Antioquia'), 'MED', TRUE, 'admin'),
('Bello', (SELECT id FROM departamentos WHERE nombre = 'Antioquia'), 'BEL', TRUE, 'admin'),
('Arauca', (SELECT id FROM departamentos WHERE nombre = 'Arauca'), 'ARA', TRUE, 'admin'),
('Arauquita', (SELECT id FROM departamentos WHERE nombre = 'Arauca'), 'ARQ', TRUE, 'admin'),
('Barranquilla', (SELECT id FROM departamentos WHERE nombre = 'Atlántico'), 'BAQ', TRUE, 'admin'),
('Soledad', (SELECT id FROM departamentos WHERE nombre = 'Atlántico'), 'SOL', TRUE, 'admin'),
('Cartagena', (SELECT id FROM departamentos WHERE nombre = 'Bolívar'), 'CTG', TRUE, 'admin'),
('Magangué', (SELECT id FROM departamentos WHERE nombre = 'Bolívar'), 'MAG', TRUE, 'admin'),
('Tunja', (SELECT id FROM departamentos WHERE nombre = 'Boyacá'), 'TUN', TRUE, 'admin'),
('Duitama', (SELECT id FROM departamentos WHERE nombre = 'Boyacá'), 'DUI', TRUE, 'admin'),
('Manizales', (SELECT id FROM departamentos WHERE nombre = 'Caldas'), 'MAN', TRUE, 'admin'),
('Villamaría', (SELECT id FROM departamentos WHERE nombre = 'Caldas'), 'VIL', TRUE, 'admin'),
('Florencia', (SELECT id FROM departamentos WHERE nombre = 'Caquetá'), 'FLO', TRUE, 'admin'),
('San Vicente del Caguán', (SELECT id FROM departamentos WHERE nombre = 'Caquetá'), 'SVC', TRUE, 'admin'),
('Yopal', (SELECT id FROM departamentos WHERE nombre = 'Casanare'), 'YOP', TRUE, 'admin'),
('Aguazul', (SELECT id FROM departamentos WHERE nombre = 'Casanare'), 'AGZ', TRUE, 'admin'),
('Popayán', (SELECT id FROM departamentos WHERE nombre = 'Cauca'), 'POP', TRUE, 'admin'),
('Santander de Quilichao', (SELECT id FROM departamentos WHERE nombre = 'Cauca'), 'SDQ', TRUE, 'admin'),
('Valledupar', (SELECT id FROM departamentos WHERE nombre = 'Cesar'), 'VAL', TRUE, 'admin'),
('Aguachica', (SELECT id FROM departamentos WHERE nombre = 'Cesar'), 'AGC', TRUE, 'admin'),
('Quibdó', (SELECT id FROM departamentos WHERE nombre = 'Chocó'), 'QUI', TRUE, 'admin'),
('Istmina', (SELECT id FROM departamentos WHERE nombre = 'Chocó'), 'IST', TRUE, 'admin'),
('Montería', (SELECT id FROM departamentos WHERE nombre = 'Córdoba'), 'MON', TRUE, 'admin'),
('Cereté', (SELECT id FROM departamentos WHERE nombre = 'Córdoba'), 'CER', TRUE, 'admin'),
('Fusagasugá', (SELECT id FROM departamentos WHERE nombre = 'Cundinamarca'), 'FUS', TRUE, 'admin'),
('Bogotá', (SELECT id FROM departamentos WHERE nombre = 'Cundinamarca'), 'BOG', TRUE, 'admin'),
('Inírida', (SELECT id FROM departamentos WHERE nombre = 'Guainía'), 'INI', TRUE, 'admin'),
('Barranco Minas', (SELECT id FROM departamentos WHERE nombre = 'Guainía'), 'BAM', TRUE, 'admin'),
('San José del Guaviare', (SELECT id FROM departamentos WHERE nombre = 'Guaviare'), 'SJG', TRUE, 'admin'),
('Miraflores', (SELECT id FROM departamentos WHERE nombre = 'Guaviare'), 'MIR', TRUE, 'admin'),
('Neiva', (SELECT id FROM departamentos WHERE nombre = 'Huila'), 'NEI', TRUE, 'admin'),
('Pitalito', (SELECT id FROM departamentos WHERE nombre = 'Huila'), 'PIT', TRUE, 'admin'),
('Riohacha', (SELECT id FROM departamentos WHERE nombre = 'La Guajira'), 'RIO', TRUE, 'admin'),
('Maicao', (SELECT id FROM departamentos WHERE nombre = 'La Guajira'), 'MAI', TRUE, 'admin'),
('Santa Marta', (SELECT id FROM departamentos WHERE nombre = 'Magdalena'), 'STA', TRUE, 'admin'),
('Ciénaga', (SELECT id FROM departamentos WHERE nombre = 'Magdalena'), 'CIE', TRUE, 'admin'),
('Villavicencio', (SELECT id FROM departamentos WHERE nombre = 'Meta'), 'VIL', TRUE, 'admin'),
('Acacías', (SELECT id FROM departamentos WHERE nombre = 'Meta'), 'ACA', TRUE, 'admin'),
('Pasto', (SELECT id FROM departamentos WHERE nombre = 'Nariño'), 'PAS', TRUE, 'admin'),
('Ipiales', (SELECT id FROM departamentos WHERE nombre = 'Nariño'), 'IPI', TRUE, 'admin'),
('Cúcuta', (SELECT id FROM departamentos WHERE nombre = 'Norte de Santander'), 'CUC', TRUE, 'admin'),
('Ocaña', (SELECT id FROM departamentos WHERE nombre = 'Norte de Santander'), 'OCA', TRUE, 'admin'),
('Mocoa', (SELECT id FROM departamentos WHERE nombre = 'Putumayo'), 'MOC', TRUE, 'admin'),
('Puerto Asís', (SELECT id FROM departamentos WHERE nombre = 'Putumayo'), 'PUA', TRUE, 'admin'),
('Armenia', (SELECT id FROM departamentos WHERE nombre = 'Quindío'), 'ARM', TRUE, 'admin'),
('Montenegro', (SELECT id FROM departamentos WHERE nombre = 'Quindío'), 'MON', TRUE, 'admin'),
('Pereira', (SELECT id FROM departamentos WHERE nombre = 'Risaralda'), 'PER', TRUE, 'admin'),
('Dosquebradas', (SELECT id FROM departamentos WHERE nombre = 'Risaralda'), 'DOS', TRUE, 'admin'),
('San Andrés', (SELECT id FROM departamentos WHERE nombre = 'San Andrés y Providencia'), 'SAD', TRUE, 'admin'),
('Providencia', (SELECT id FROM departamentos WHERE nombre = 'San Andrés y Providencia'), 'PRO', TRUE, 'admin'),
('Bucaramanga', (SELECT id FROM departamentos WHERE nombre = 'Santander'), 'BUC', TRUE, 'admin'),
('Barrancabermeja', (SELECT id FROM departamentos WHERE nombre = 'Santander'), 'BAR', TRUE, 'admin'),
('Sincelejo', (SELECT id FROM departamentos WHERE nombre = 'Sucre'), 'SIN', TRUE, 'admin'),
('Corozal', (SELECT id FROM departamentos WHERE nombre = 'Sucre'), 'COR', TRUE, 'admin'),
('Ibagué', (SELECT id FROM departamentos WHERE nombre = 'Tolima'), 'IBA', TRUE, 'admin'),
('Espinal', (SELECT id FROM departamentos WHERE nombre = 'Tolima'), 'ESP', TRUE, 'admin'),
('Cali', (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca'), 'CAL', TRUE, 'admin'),
('Palmira', (SELECT id FROM departamentos WHERE nombre = 'Valle del Cauca'), 'PAL', TRUE, 'admin'),
('Mitú', (SELECT id FROM departamentos WHERE nombre = 'Vaupés'), 'MIT', TRUE, 'admin'),
('Carurú', (SELECT id FROM departamentos WHERE nombre = 'Vaupés'), 'CAR', TRUE, 'admin'),
('Puerto Carreño', (SELECT id FROM departamentos WHERE nombre = 'Vichada'), 'PUC', TRUE, 'admin'),
('La Primavera', (SELECT id FROM departamentos WHERE nombre = 'Vichada'), 'LAP', TRUE, 'admin');

INSERT INTO sedes (nombre, direccion, ciudad_id, telefono, habilitado, created_by) VALUES
('Sede Principal Cali', 'Carrera 1 # 2-34', (SELECT id FROM ciudades WHERE nombre = 'Cali'), '3000000001', TRUE, 'admin'),
('Sede Norte Cali', 'Avenida 6 Norte # 45-67', (SELECT id FROM ciudades WHERE nombre = 'Cali'), '3000000002', TRUE, 'admin'),
('Sede Sur Cali', 'Calle 13 # 100-21', (SELECT id FROM ciudades WHERE nombre = 'Cali'), '3000000003', TRUE, 'admin'),
('Sede Centro Cali', 'Calle 9 # 5-67', (SELECT id FROM ciudades WHERE nombre = 'Cali'), '3000000004', TRUE, 'admin'),
('Sede Principal Bogotá', 'Carrera 7 # 12-34', (SELECT id FROM ciudades WHERE nombre = 'Bogotá'), '3000000005', TRUE, 'admin'),
('Sede Norte Bogotá', 'Avenida 9 # 100-23', (SELECT id FROM ciudades WHERE nombre = 'Bogotá'), '3000000006', TRUE, 'admin'),
('Sede Sur Bogotá', 'Calle 40 Sur # 10-15', (SELECT id FROM ciudades WHERE nombre = 'Bogotá'), '3000000007', TRUE, 'admin'),
('Sede Centro Bogotá', 'Calle 26 # 5-32', (SELECT id FROM ciudades WHERE nombre = 'Bogotá'), '3000000008', TRUE, 'admin'),
('Sede Principal Medellín', 'Avenida Oriental # 10-45', (SELECT id FROM ciudades WHERE nombre = 'Medellín'), '3000000009', TRUE, 'admin'),
('Sede Norte Medellín', 'Carrera 70 # 45-56', (SELECT id FROM ciudades WHERE nombre = 'Medellín'), '3000000010', TRUE, 'admin'),
('Sede Sur Medellín', 'Avenida Las Vegas # 20-30', (SELECT id FROM ciudades WHERE nombre = 'Medellín'), '3000000011', TRUE, 'admin'),
('Sede Centro Medellín', 'Calle 50 # 30-20', (SELECT id FROM ciudades WHERE nombre = 'Medellín'), '3000000012', TRUE, 'admin');

INSERT INTO listas (nombre, descripcion, habilitado, created_by) VALUES
('Tipo de Identificación', 'Lista de tipos de identificación para usuarios', TRUE, 'admin'),
('Tipo de Trabajo', 'Lista de tipos de contratos de trabajo', TRUE, 'admin'),
('Profesión', 'Lista de profesiones disponibles para los usuarios', TRUE, 'admin'),
('Estado Civil', 'Lista de estados civiles para los usuarios', TRUE, 'admin'),
('Nivel Educativo', 'Lista de niveles educativos alcanzados por los usuarios', TRUE, 'admin'),
('Género', 'Lista de géneros disponibles para los usuarios', TRUE, 'admin');

INSERT INTO valores_listas (lista_id, valor, descripcion, orden, habilitado, created_by) VALUES
((SELECT id FROM listas WHERE nombre = 'Tipo de Identificación'), 'Cédula de Ciudadanía', 'Documento de identificación nacional', 1, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Tipo de Identificación'), 'Pasaporte', 'Documento de identificación internacional', 2, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Tipo de Identificación'), 'Tarjeta de Identidad', 'Documento de identificación para menores de edad', 3, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Tipo de Identificación'), 'Cédula de Extranjería', 'Documento de identificación para extranjeros', 4, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Tipo de Trabajo'), 'Contrato a término indefinido', 'Contrato de trabajo sin fecha de terminación', 1, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Tipo de Trabajo'), 'Contrato a término fijo', 'Contrato de trabajo con fecha de terminación establecida', 2, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Tipo de Trabajo'), 'Prestación de servicios', 'Contrato por prestación de servicios específicos', 3, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Tipo de Trabajo'), 'Prácticas', 'Contrato para prácticas profesionales o pasantías', 4, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Tipo de Trabajo'), 'Contrato de aprendizaje', 'Contrato para procesos de aprendizaje', 5, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Profesión'), 'Ingeniero', 'Profesional en ingeniería', 1, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Profesión'), 'Médico', 'Profesional de la salud', 2, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Profesión'), 'Abogado', 'Profesional del derecho', 3, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Profesión'), 'Contador', 'Profesional en contaduría', 4, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Profesión'), 'Arquitecto', 'Profesional en arquitectura', 5, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Profesión'), 'Docente', 'Profesional de la educación', 6, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Profesión'), 'Enfermero', 'Profesional en enfermería', 7, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Estado Civil'), 'Soltero', 'Usuario no está casado', 1, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Estado Civil'), 'Casado', 'Usuario está casado', 2, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Estado Civil'), 'Divorciado', 'Usuario está divorciado', 3, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Estado Civil'), 'Viudo', 'Usuario ha perdido a su cónyuge', 4, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Estado Civil'), 'Unión Libre', 'Usuario vive con su pareja sin estar casado', 5, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Nivel Educativo'), 'Primaria', 'Usuario tiene estudios de primaria', 1, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Nivel Educativo'), 'Secundaria', 'Usuario tiene estudios de secundaria', 2, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Nivel Educativo'), 'Técnico', 'Usuario tiene estudios técnicos', 3, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Nivel Educativo'), 'Tecnológico', 'Usuario tiene estudios tecnológicos', 4, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Nivel Educativo'), 'Universitario', 'Usuario tiene estudios universitarios', 5, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Nivel Educativo'), 'Postgrado', 'Usuario tiene estudios de postgrado', 6, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Género'), 'Masculino', 'Género masculino', 1, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Género'), 'Femenino', 'Género femenino', 2, TRUE, 'admin'),
((SELECT id FROM listas WHERE nombre = 'Género'), 'Otro', 'Otro género o no especificado', 5, TRUE, 'admin');

INSERT INTO usuarios (
    identificacion_id,
    numero_identificacion,
    nombres,
    correo,
    password,
    telefono,
    direccion,
    ciudad_residencia_id,
    profesion_id,
    tipo_trabajo_id,
    estado_civil_id,
    nivel_educativo_id,
    ingresos,
    egresos,
    genero_id,
    habilitado,
    created_at,
    created_by
) VALUES (
    4,                                -- identificacion_id
    '123456755879',                   -- numero_identificacion
    'Ricardo Milos',                  -- nombres
    'ricardo.milos@example.com',     -- correo
    '$2a$10$n70LMB19L0yezKtvp94GROEHWM7wpWnMifH5FlUr6lIs0ZkUUMARO', -- password (123)
    '555-1234',                       -- telefono
    'Calle 123, Ciudad de Ejemplo',   -- direccion
    59,                               -- ciudad_residencia_id
    13,                               -- profesion_id
    13,                               -- tipo_trabajo_id
    20,                               -- estado_civil_id
    29,                               -- nivel_educativo_id
    5000.00,                          -- ingresos
    2000.00,                          -- egresos
    31,                               -- genero_id
    true,                             -- habilitado
    CURRENT_TIMESTAMP,                -- created_at (fecha de creación)
    'admin'                           -- created_by (puedes cambiar esto según el contexto)
);

INSERT INTO roles (nombre, descripcion, habilitado, created_at, created_by)
VALUES ('Administrador', 'Rol con acceso total al sistema', TRUE, CURRENT_TIMESTAMP, 1);
INSERT INTO roles (nombre, descripcion, habilitado, created_at, created_by)
VALUES ('Cajero', 'Responsable de realizar transacciones bancarias directas con los clientes', TRUE, CURRENT_TIMESTAMP, 1);
INSERT INTO roles (nombre, descripcion, habilitado, created_at, created_by)
VALUES ('Gerente de Sucursal', 'Responsable de la administración y supervisión general de una sucursal bancaria', TRUE, CURRENT_TIMESTAMP, 1);
INSERT INTO roles (nombre, descripcion, habilitado, created_at, created_by)
VALUES ('Oficial de Crédito', 'Evaluar y recomendar la aprobación o denegación de solicitudes de crédito', TRUE, CURRENT_TIMESTAMP, 1);
INSERT INTO roles (nombre, descripcion, habilitado, created_at, created_by)
VALUES ('Analista de Riesgos', 'Identificación, evaluación y análisis de riesgos financieros y de crédito', TRUE, CURRENT_TIMESTAMP, 1);
INSERT INTO roles (nombre, descripcion, habilitado, created_at, created_by)
VALUES ('Servicio al Cliente', 'Brindar asistencia y soporte a los clientes para resolver sus consultas bancarias', TRUE, CURRENT_TIMESTAMP, 1);
INSERT INTO roles (nombre, descripcion, habilitado, created_at, created_by)
VALUES ('Auditor', 'Revisar y evaluar la integridad de las operaciones y registros financieros del banco', TRUE, CURRENT_TIMESTAMP, 1);

-- Crear los permisos para acceder a cada módulo y asignar el módulo correspondiente
INSERT INTO permisos (nombre, descripcion, habilitado, modulo_id, created_by) VALUES
('ACCESO_MODULO_ROLES', 'Permiso para acceder a los endpoints del módulo de Roles', TRUE, (SELECT id FROM valores_listas WHERE valor = 'Roles' LIMIT 1), 'admin'),
('ACCESO_MODULO_USUARIOS', 'Permiso para acceder a los endpoints del módulo de Usuarios', TRUE, (SELECT id FROM valores_listas WHERE valor = 'Usuarios' LIMIT 1), 'admin'),
('ACCESO_MODULO_PERMISOS_ROLES', 'Permiso para acceder a los endpoints del módulo de Permisos a Roles', TRUE, (SELECT id FROM valores_listas WHERE valor = 'Permisos a Roles' LIMIT 1), 'admin');

-- Asignar estos permisos al rol administrador
INSERT INTO roles_permisos (rol_id, permiso_id, habilitado) VALUES
((SELECT id FROM roles WHERE nombre = 'Administrador'), (SELECT id FROM permisos WHERE nombre = 'ACCESO_MODULO_ROLES'), TRUE),
((SELECT id FROM roles WHERE nombre = 'Administrador'), (SELECT id FROM permisos WHERE nombre = 'ACCESO_MODULO_USUARIOS'), TRUE),
((SELECT id FROM roles WHERE nombre = 'Administrador'), (SELECT id FROM permisos WHERE nombre = 'ACCESO_MODULO_PERMISOS_ROLES'), TRUE);

INSERT INTO public.usuario_rol (usuario_id,rol_id) VALUES(1, 1);