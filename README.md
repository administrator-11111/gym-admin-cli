# Sistema de administración de Gimnasio

## Problema que queremos resolver
Actualmente, el control de los clientes en un gimansio y sus pagos se reaiza de forma manual. Esto genera desorden al momento de identificar qué usuarios tienen su mensualidad vencida, dificulta el cálculo de las ganancias mensuales y retrasa el proceso de inscripción o eliminación de socios.

Nuestro proyecto busca resoler esto mediante un sistema de consola que automatice la gestión de usurarios, permitiendo controlar las fechas de suscripción y calcular los ingresos de manera directa.


## Usuarios del sistema
El sistema está diseñado para ser utilizado exclusivamente por el Administrador del gimnasio. Este usuario es el encargado de iniciar sesión en la plataforma y realizar todas las operaciones de gestión (inscribir, quitar, revisar usuarios y ver ganancias).

## Modelo UML de Clases
Para resolver este problema, modelamos las siguientes entidades basadas en POO (Programación Orientada a Objetos), aplicando herencia y encapsulamiento.
### 1. Imprimible<\<interface\>>
- **Métodos**
  - + `resumenDatos()` : 
### 2. Usuario <\<abstract\>>
Será la clase base que abstrae las características comunes de los individuos registrados en el sistema.
- **Atributos** 
  - **#** `id` (Int)
  - **#** `nombreCompleto` (String)

### 3. Administrador
Hereda de la clase `Usuario`. Representa al personal del gimnasio que opera el sistema.
- **Atributos** 
  - **-** `nombreUsuario` (String)
  - **-** `clave` (String)
- **Métodos**
  - **+** `validarCredenciales()` (Boolean - valida el acceso al sistema)

### 4. Cliente
Hereda de la clase `Usuario`. Representa a la persona que asiste a entrenar al gimnasio.
- **Atributos** 
  - **-** `suscripcionAcual` (String)
- **Métodos**
  - **+** `esMoroso()` (Boolean - verifica si su plan caducó)

### 5. Suscripción
Entidad que gestiona el plan contratando, separando la lógica fincnciera del perfil del cliente.
- **Atributos** 
  - **-** `tipoPlan` (String)
  - **-** `costo` (Double)
  - **-** `fechaInicio` (Fecha)
  - **-** `fechaFin` (Fecha)
- **Métodos**
  - **+** `renovar(meses: Int)` (Unit)

### 6. Fecha
Clase para estructura el manejo del tiempo de las suscripciones.
- **Atributos** 
  - **-** `dia` (Int) 
  - **-** `mes` (Int) 
  - **-** `anio` (Int)
- **Métodos**
  - **+** `sumarMeses(n: Int)` (Unit)

### 7. Gestor
- **Atributos privados:** 
  - **-** `clientes` (List\<Cliente\>) 
  - **-** `administradores` (List\<Administrador\>) 
- **Atributos** 
  - **+** `iniciarSesion(usuario: String, clave: String)` (Boolean) 
  - **+** `inscribirCliente(cliente: Cliente)` (Unit)
  - **+** `quitarCliente(cliente: Cliente)` (Unit)
  - **+** `mostrarMorosos()` (Unit)
  - **+** `calcularGanancias()` (Double)