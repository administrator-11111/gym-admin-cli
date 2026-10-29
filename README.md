# Sistema de Administración de Gimnasio (`gym-admin-cli`)

Sistema de línea de comandos (CLI) desarrollado en **Kotlin** para la gestión y administración integral de socios de un gimnasio. El sistema automatiza el control de suscripciones, detección de morosidad, cálculo financiero de ingresos y persistencia de datos mediante archivos CSV.

---

## Problema que resuelve

El control manual de clientes y pagos genera inconsistencias, dificulta la identificación de mensualidades vencidas y retrasa el cálculo de ingresos del negocio. 

Este proyecto resuelve dicha problemática aplicando los principios de la **Programación Orientada a Objetos (POO)**: herencia, abstracción, polimorfismo, encapsulamiento y el patrón de diseño **Singleton**.

---

## Usuarios del Sistema

El sistema está diseñado para ser operado exclusivamente por el **Personal Administrativo** del gimnasio. 
Al iniciar la aplicación, se requiere autenticación previa mediante usuario y contraseña autorizados antes de acceder al panel de operaciones.

---

## Modelo de Clases (POO)

### 1. `Imprimible` <<*interface*>>
Define el contrato para generar una representación textual estandarizada de las entidades del sistema.
- **Métodos:**
  - `+ resumenDatos(): String`

### 2. `Usuario` <<*abstract*>>
Clase base que encapsula los atributos comunes de las personas registradas en el sistema. Implementa `Imprimible`.
- **Atributos protegidos:**
  - `# id: Int`
  - `# nombreCompleto: String`
- **Métodos:**
  - `+ resumenDatos(): String` (implementación base)

### 3. `Administrador`
Hereda de `Usuario`. Representa al personal del gimnasio autorizado para operar el sistema.
- **Atributos privados:**
  - `- nombreUsuario: String`
  - `- clave: String`
- **Métodos:**
  - `+ validarCredenciales(usuario: String, clave: String): Boolean`
  - `+ resumenDatos(): String` (sobrescrito con polimorfismo)

### 4. `Cliente`
Hereda de `Usuario`. Representa al socio que asiste a entrenar y posee una suscripción activa.
- **Atributos privados:**
  - `- suscripcionActual: Suscripcion`
- **Métodos:**
  - `+ obtenerSuscripcion(): Suscripcion`
  - `+ obtenerCostoSuscripcion(): Double`
  - `+ esMoroso(): Boolean` (compara la fecha de término con la fecha actual del sistema)
  - `+ resumenDatos(): String` (sobrescrito con polimorfismo)

### 5. `Suscripcion`
Entidad que gestiona el plan contratado, separando la lógica temporal y financiera del perfil del cliente.
- **Atributos privados:**
  - `- tipoPlan: String` (Mensual, Trimestral o Anual)
  - `- costo: Double`
  - `- fechaInicio: Fecha`
  - `- fechaFin: Fecha`
- **Métodos:**
  - `+ obtenerTipoPlan(): String`
  - `+ obtenerCosto(): Double`
  - `+ obtenerFechaInicio(): Fecha`
  - `+ obtenerFechaFin(): Fecha`
  - `+ renovar(meses: Int): Unit`

### 6. `Fecha`
Clase personalizada para el manejo del tiempo, cálculo de vencimientos y validación de rangos válidos y años bisiestos.
- **Atributos privados:**
  - `- dia: Int`
  - `- mes: Int`
  - `- anio: Int`
- **Métodos:**
  - `+ aLocalDate(): LocalDate`
  - `+ sumarMeses(n: Int): Unit`
  - `+ toString(): String` (formato `DD/MM/AAAA`)

### 7. `Gestor` <<*singleton*>>
Controlador central del sistema implementado como `object` (Singleton). Centraliza las colecciones en memoria y coordina la persistencia de datos.
- **Atributos privados:**
  - `- clientes: MutableList<Cliente>`
  - `- administradores: MutableList<Administrador>`
- **Métodos principales:**
  - `+ registrarAdministrador(admin: Administrador): Unit`
  - `+ iniciarSesion(usuario: String, clave: String): Boolean`
  - `+ cargarDatos(rutaArchivo: String): Unit` (carga inicial masiva)
  - `+ inscribirCliente(cliente: Cliente): Boolean`
  - `+ quitarCliente(id: Int): Boolean`
  - `+ buscarCliente(id: Int): Cliente?`
  - `+ actualizarCliente(clienteActualizado: Cliente): Boolean`
  - `+ listarClientes(): Unit`
  - `+ mostrarMorosos(): Unit`
  - `+ calcularGanancias(): Double`
  - `+ obtenerSiguienteId(rutaArchivo: String): Int`
  - `+ reescribirCsv(rutaArchivo: String): Unit`

### 8. `Menu`
Gestiona la interfaz interactiva por consola (bucle REPL), validación de entradas de usuario y navegación por submenús.
- **Métodos:**
  - `+ ejecutar(): Unit`
  - `- mostrarMenu(): Unit`
  - `- limpiarConsola(): Unit`
  - `- gestionarListaClientes(): Unit` (submenú con opciones de seleccionar, editar y quitar)
  - `- editarClientePasoAPaso(cliente: Cliente): Unit`
  - `- inscribirClienteDesdeConsola(): Unit`

---