# Happy Feet Veterinaria - Sistema de Gestión Veterinaria
## Descripción del Contexto
El Sistema de Gestión Veterinaria "Happy Feet" es una aplicación desarrollada para automatizar y optimizar los procesos administrativos y médicos de una clínica veterinaria. El sistema resuelve problemas como la gestión manual de citas, el control de historiales médicos, el inventario de medicamentos y productos, y el proceso de adopción de mascotas, proporcionando una solución integral que mejora la eficiencia y la calidad del servicio.

## Estructura del Proyecto
```
/HappyFeet_Veterinaria_ApellidoNombre
│
├── /database
│   ├── schema.sql
│   └── data.sql
│
├── /src
│   └── /main/java/com/happyfeet
│       │
│       ├── /controller
│       │   ├── AdopcionController.java
│       │   ├── CitaController.java
│       │   ├── ConsultaMedicaController.java
│       │   ├── DuenoController.java
│       │   ├── FacturacionController.java
│       │   ├── HistorialMedicoController.java
│       │   ├── InventarioController.java
│       │   ├── JornadasVacunacionController.java
│       │   ├── MascotaAdopcionController.java
│       │   ├── MascotaController.java
│       │   ├── ProcedimientoEspecialController.java
│       │   ├── ProveedorController.java
│       │   ├── RegistroJornadaVacunacionController.java
│       │   ├── ReporteController.java
│       │   └── VeterinarioController.java
│       │
│       ├── /model
│       │   ├── /entities
│       │   │   ├── Adopcion.java
│       │   │   ├── BaseEntity.java
│       │   │   ├── Cita.java
│       │   │   ├── ConsultaMedica.java
│       │   │   ├── Dueno.java
│       │   │   ├── Factura.java
│       │   │   ├── HistorialMedicoEspecial.java
│       │   │   ├── Inventario.java
│       │   │   ├── ItemFactura.java
│       │   │   ├── JornadasVacunacion.java
│       │   │   ├── Mascota.java
│       │   │   ├── MascotaAdopcion.java
│       │   │   ├── ProcedimientoEspecial.java
│       │   │   ├── Proveedor.java
│       │   │   ├── RegistroJornadaVacunacion.java
│       │   │   ├── Servicio.java
│       │   │   └── Veterinario.java
│       │   │
│       │   └── /enums
│       │       # (Aquí puedes agregar tus enumeraciones si las necesitas)
│       │
│       ├── /repository
│       │   ├── AdopcionDAO.java
│       │   ├── BaseDAO.java
│       │   ├── CitaDAO.java
│       │   ├── ConsultaMedicaDAO.java
│       │   ├── DuenoDAO.java
│       │   ├── FacturaDAO.java
│       │   ├── HistorialMedicoDAO.java
│       │   ├── InventarioDAO.java
│       │   ├── JornadasVacunacionDAO.java
│       │   ├── MascotaAdopcionDAO.java
│       │   ├── MascotaDAO.java
│       │   ├── ProcedimientoEspecialDAO.java
│       │   ├── ProveedorDAO.java
│       │   ├── RegistroJornadaVacunacionDAO.java
│       │   ├── ReporteDAO.java
│       │   ├── ServicioDAO.java
│       │   └── VeterinarioDAO.java
│       │
│       ├── /service
│       │   ├── AdopcionService.java
│       │   ├── CitaService.java
│       │   ├── ConsultaMedicaService.java
│       │   ├── DuenoService.java
│       │   ├── FacturacionService.java
│       │   ├── HistorialMedicoService.java
│       │   ├── InventarioService.java
│       │   ├── JornadasVacunacionService.java
│       │   ├── MascotaAdopcionService.java
│       │   ├── MascotaService.java
│       │   ├── ProcedimientoEspecialService.java
│       │   ├── ProveedorService.java
│       │   ├── RegistroJornadaVacunacionService.java
│       │   ├── ReporteService.java
│       │   └── VeterinarioService.java
│       │
│       ├── /view
│       │   # (Aquí van las clases de interfaz de consola)
│       │
│       ├── /util
│       │   ├── DatabaseConnection.java
│       │   └── LogConfig.java
│       │
│       └── /exception
│           └── VeterinariaException.java
│
├── .gitignore
├── pom.xml
└── README.md
```


## Tecnologías Utilizadas
Java 17 - Lenguaje de programación principal

MySQL 8.0 - Sistema de gestión de base de datos

JDBC - Conector para base de datos

Maven - Gestión de dependencias y construcción del proyecto

Git - Control de versiones

Patrón MVC - Arquitectura de software

## Funcionalidades Implementadas
Módulos Principales:
1. Gestión de Mascotas y Dueños
* Registro y actualización de mascotas y sus dueños

* Control de información médica básica

* Historial completo de mascotas

2. Sistema de Citas
* Programación y gestión de citas médicas

* Asignación de veterinarios

* Recordatorios de próximas citas

3. Historial Médico
* Registro de consultas médicas

* Control de procedimientos especiales

* Seguimiento de tratamientos

4. Gestión de Inventario
* Control de stock de medicamentos

* Gestión de proveedores

* Alertas de inventario bajo

5. Jornadas de Vacunación
* Programación de campañas de vacunación

* Registro de vacunas aplicadas

* Reportes de cobertura de vacunación

6. Proceso de Adopción
* Registro de mascotas en adopción

* Gestión de solicitudes de adopción

* Seguimiento post-adopción

7. Facturación
* Generación de facturas automáticas

* Control de servicios prestados

* Gestión de pagos

## Reglas de Negocio Importantes:
* Validación de disponibilidad de veterinarios para citas

* Control de stock mínimo para medicamentos críticos

* Verificación de esquema de vacunación completo

* Validación de datos médicos obligatorios

* Control de permisos y accesos

## Modelo de la Base de Datos
El sistema utiliza un modelo relacional con las siguientes tablas principales:

## Tablas Principales:
* Mascota: Información de las mascotas (nombre, especie, raza, edad)

* Dueno: Datos de los propietarios

* Veterinario: Información del personal médico

* Cita: Programación de consultas

* ConsultaMedica: Registro de diagnósticos y tratamientos

* Inventario: Control de medicamentos y productos

* Factura: Gestión de pagos y servicios

* MascotaAdopcion: Mascotas disponibles para adopción

## Relaciones:
* Una mascota pertenece a un dueño

* Un dueño puede tener múltiples mascotas

* Un veterinario atiende múltiples citas

* Una mascota puede tener múltiples consultas médicas

* Una factura puede contener múltiples servicios

![Vista previa del sitio](./happy_feet_veterinaria.webp)

## Instrucciones de Instalación y Ejecución
### Requisitos Previos
* JDK 17 o superior

* Maven 3.6 o superior

* MySQL Server 8.0 o superior

* Git

## Clonación del Proyecto
```
git clone https://github.com/arleyk/HappyFeet_Veterinaria_EstupinanArley.git
cd HappyFeet_Veterinaria_EstupinanArley
```

## Configuración de la Base de Datos
* Iniciar MySQL Server

* Crear la base de datos:
```
sql
CREATE DATABASE happyfeet_veterinaria;
```

## Configurar conexión en src/main/resources/application.properties:

```
properties
db.url=jdbc:mysql://localhost:3306/happyfeet_veterinaria
db.username=tu_usuario
db.password=tu_contraseña
```

## Ejecución de Scripts SQL
* Ejecutar script de estructura:
```
bash
mysql -u usuario -p happyfeet_veterinaria < database/schema.sql
```

* Ejecutar script de datos iniciales:

```
bash
mysql -u usuario -p happyfeet_veterinaria < database/data.sql
```

## Ejecución del Proyecto
* Compilar el proyecto:
```
bash
mvn clean compile
```

* Ejecutar la aplicación:
```
bash
mvn exec:java -Dexec.mainClass="com.happyfeet.Main"
```

* O alternativamente:
```
bash
mvn spring-boot:run
```

## Guía de Uso
### Menú Principal
Al ejecutar la aplicación, se presentará un menú principal con las siguientes opciones:

1. Gestión de Mascotas y Dueños

* Registrar nueva mascota

* Actualizar información

* Consultar historial

2. Sistema de Citas

* Programar nueva cita

* Consultar citas pendientes

* Cancelar citas

3. Historial Médico

* Registrar consulta

* Ver historial completo

* Actualizar tratamientos

4. Inventario

* Consultar stock

* Realizar pedidos

* Gestionar proveedores

5. Adopciones

* Ver mascotas disponibles

* Solicitar adopción

* Seguimiento post-adopción

## Navegación:
* Use las teclas numéricas para seleccionar opciones

* Presione '0' para volver al menú anterior

* Presione '9' para salir del sistema

## Autor(es)
Arley Estupiñán
Laura Ardila 
Kevin Maestre 

GitHub: arleyk

Proyecto: HappyFeet Veterinaria

