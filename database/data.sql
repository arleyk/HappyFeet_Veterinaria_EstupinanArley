-- Especies

INSERT INTO especies (nombre, descripcion) VALUES

('Perro', 'Canis lupus familiaris'),

('Gato', 'Felis catus'),

('Ave', 'Aves domésticas'),

('Roedor', 'Pequeños mamíferos roedores'),

('Reptil', 'Reptiles domésticos'),

('Conejo', 'Oryctolagus cuniculus');



-- Razas (ejemplos básicos)

INSERT INTO razas (especie_id, nombre, caracteristicas) VALUES

(1, 'Labrador Retriever', 'Tamaño grande, amigable, energético'),

(1, 'Golden Retriever', 'Tamaño grande, inteligente, leal'),

(1, 'Pastor Alemán', 'Tamaño grande, inteligente, protector'),

(1, 'Bulldog Francés', 'Tamaño pequeño, cariñoso, juguetón'),

(1, 'Chihuahua', 'Tamaño muy pequeño, alerta, valiente'),

(1, 'Poodle', 'Inteligente, hipoalergénico'),

(1, 'Mestizo', 'Cruza de razas'),

(2, 'Persa', 'Pelo largo, tranquilo'),

(2, 'Siamés', 'Vocal, social, activo'),

(2, 'Maine Coon', 'Grande, peludo, amigable'),

(2, 'Bengalí', 'Activo, manchas de leopardo'),

(2, 'Mestizo', 'Cruza de razas'),

(3, 'Canario', 'Pequeño, cantante'),

(3, 'Periquito', 'Pequeño, social'),

(3, 'Loro', 'Inteligente, longevo'),

(4, 'Hámster Sirio', 'Pequeño, nocturno'),

(4, 'Cobayo', 'Mediano, social'),

(5, 'Iguana Verde', 'Herbívoro, grande'),

(5, 'Tortuga', 'Lenta, longeva'),

(6, 'Conejo Enano', 'Pequeño, dócil'),

(6, 'Conejo Gigante', 'Grande, tranquilo');



-- Tipos de productos

INSERT INTO producto_tipos (nombre, descripcion) VALUES

('Medicamento', 'Fármacos y medicinas veterinarias'),

('Vacuna', 'Vacunas y biológicos'),

('Insumo Médico', 'Material médico y quirúrgico'),

('Alimento', 'Alimento para mascotas'),

('Accesorio', 'Accesorios y productos de cuidado');



-- Tipos de eventos médicos

INSERT INTO evento_tipos (nombre 	, descripcion) VALUES

('Vacunación', 'Aplicación de vacunas'),

('Consulta General', 'Consulta veterinaria general'),

('Cirugía', 'Procedimiento quirúrgico'),

('Desparasitación', 'Tratamiento antiparasitario'),

('Control de Peso', 'Seguimiento de peso'),

('Examen de Sangre', 'Análisis de laboratorio'),

('Radiografía', 'Estudio de imagen'),

('Emergencia', 'Atención de emergencia'),

('Limpieza Dental', 'Profilaxis dental');



-- Estados de citas

INSERT INTO cita_estados (nombre, descripcion) VALUES

('Programada', 'Cita agendada pendiente'),

('Confirmada', 'Cita confirmada por el cliente'),

('En Proceso', 'Mascota siendo atendida'),

('Finalizada', 'Cita completada'),

('Cancelada', 'Cita cancelada'),

('No Asistió', 'Cliente no se presentó');