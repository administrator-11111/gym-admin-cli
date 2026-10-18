
fun main() {
    println("hola :)")

    Cliente(1, "Juan Pérez", Suscripcion("Mensual", 20000.0,  Fecha(1, 10, 2026),  Fecha(30, 10, 2026))),
    Cliente(2, "María González", Suscripcion("Mensual", 20000.0,  Fecha(1, 9, 2026),  Fecha(30, 9, 2026))), // Moroso
    Cliente(3, "Pedro Soto", Suscripcion("Mensual", 20000.0,  Fecha(15, 10, 2026),  Fecha(15, 11, 2026))),
    Cliente(4, "Ana Rojas", Suscripcion("Mensual", 22000.0, Fecha(1, 8, 2026),  Fecha(31, 8, 2026))), // Moroso
    Cliente(5, "Luis Muñoz", Suscripcion("Mensual", 20000.0, Fecha(5, 10, 2026), Fecha(5, 11, 2026))),

    // --- CLIENTES CON PLAN TRIMESTRAL ---
    Cliente(6, "Carla Díaz", Suscripcion("Trimestral", 50000.0, Fecha(1, 10, 2026),  Fecha(31, 12, 2026))),
    Cliente(7, "Diego Contreras", Suscripcion("Trimestral", 50000.0,  Fecha(1, 7, 2026),  Fecha(30, 9, 2026))), // Moroso
    Cliente(8, "Sofía Silva", Suscripcion("Trimestral", 55000.0,  Fecha(10, 9, 2026),  Fecha(10, 12, 2026))),
    Cliente(9, "Andrés Sepúlveda", Suscripcion("Trimestral", 50000.0,  Fecha(1, 10, 2026),  Fecha(31, 12, 2026))),
    Cliente(10, "Valentina Morales", Suscripcion("Trimestral", 50000.0,  Fecha(20, 10, 2026),Fecha(20, 1, 2027))),
    Cliente(11, "Matías Castro", Suscripcion("Trimestral", 50000.0,  Fecha(1, 6, 2026),  Fecha(31, 8, 2026))), // Moroso
    Cliente(12, "Camila Herrera", Suscripcion("Trimestral", 52000.0,  Fecha(15, 10, 2026),  Fecha(15, 1, 2027))),

    // --- CLIENTES CON PLAN ANUAL ---
    Cliente(13, "Sebastián Fuentes", Suscripcion("Anual", 180000.0,  Fecha(1, 1, 2026), Fecha(31, 12, 2026))),
    Cliente(14, "Javiera Araya", Suscripcion("Anual", 180000.0,  Fecha(1, 10, 2026),  Fecha(30, 9, 2027))),
    Cliente(15, "Felipe Gutiérrez", Suscripcion("Anual", 180000.0,  Fecha(15, 3, 2026),  Fecha(15, 3, 2027))),
    Cliente(16, "Daniela Torres", Suscripcion("Anual", 190000.0,  Fecha(1, 5, 2026),  Fecha(30, 4, 2027))),
    Cliente(17, "Cristóbal Espinoza", Suscripcion("Anual", 180000.0, Fecha(1, 1, 2025),  Fecha(31, 12, 2025))), // Moroso
    Cliente(18, "Constanza Valdés", Suscripcion("Anual", 180000.0,  Fecha(1, 11, 2026),  Fecha(31, 10, 2027))),
    Cliente(19, "Ignacio Riquelme", Suscripcion("Anual", 180000.0,  Fecha(1, 8, 2026), Fecha(31, 7, 2027))),
    Cliente(20, "Fernanda Pizarro", Suscripcion("Anual", 200000.0,  Fecha(1, 10, 2026),  Fecha(30, 9, 2027)))
}