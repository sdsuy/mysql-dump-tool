# MySQL Dump Tool (Java)

Una herramienta de línea de comandos en Java para conectarse a un servidor MySQL, listar las bases de datos disponibles y generar un **dump completo (estructura y datos)** de una base seleccionada por el usuario utilizando `mysqldump`.

---

## 🧩 Características

- Conexión mediante MySQL Connector/J
- Listado interactivo de bases de datos accesibles por el usuario
- Detección de versión del servidor MySQL
- Generación de dumps en archivos `.sql` con timestamp
- Archivos guardados en una carpeta `dumps/`
- Separación por responsabilidades e inyección de dependencias

---

## 📦 Estructura del proyecto

```
src/
├── App.java                     # Clase principal con el método main()
├── MySQLConnector.java          # Encapsula conexión y consultas a MySQL
└── MySQLDumper.java             # Ejecuta el comando mysqldump
lib/
└── mysql-connector-j-8.x.x.jar  # MySQL Connector/J
bin/
├── App.class                    # Clase principal con el método main()
├── MySQLConnector.class         # Encapsula conexión y consultas a MySQL
└── MySQLDumper.class            # Ejecuta el comando mysqldump
dumps/
└── *.sql                        # Dumps de salida
```

---

## ⚙️ Requisitos

- Java 8 o superior
- Conector JDBC (MySQL Connector/J)
- MySQL instalado (con acceso al comando `mysqldump`)

---

## 🔧 Instalación

1. **Cloná el repositorio:**

```git
git clone https://github.com/tuusuario/mysql-dump-tool.git
cd mysql-dump-tool/src
```

2. **Agregá el MySQL Connector/J al classpath**  
   Descargalo desde: https://dev.mysql.com/downloads/connector/j/

3. 

### 🪟 Instrucciones para PowerShell (Windows)

```powershell
# Compilación
mkdir bin
javac -cp ".;\lib\mysql-connector-j-8.x.x.jar" -d ".\bin" .\src\*.java

# Ejecución
java -cp ".;.\bin;.\lib\mysql-connector-j-8.x.x.jar" App <host> <usuario>

# Ejemplo
java -cp ".;.\bin;.\lib\mysql-connector-j-8.2.0.jar" App localhost root
```

---

## 📦 Empaquetado en JAR

Para crear un archivo `.jar` ejecutable:

En PowerShell (Windows):

```powershell
jar cfe MySQLDumpTool.jar App -C .\bin\ App.class -C .\bin\ MySQLConnector.class -C .\bin\ MySQLDumper.class
```

---

## ▶️ Ejecutar el JAR

### En Windows (PowerShell):

```powershell
java -cp "MySQLDumpTool.jar;.\lib\mysql-connector-j-8.x.x.jar" App <host> <usuario>
```

---

## 🔐 Seguridad

- La contraseña se solicita de forma interactiva al iniciar la aplicación, no se pasa como argumento.

---

## 🗃️ Salida

- Los dumps se generan con nombres como:

```
dumps/clientes_2025-06-14_20-32-12_dump.sql
```

---

## 📘 Ejemplo de uso

```powershell
$ java {...} App localhost root
Contraseña para el usuario root: ******

Versión del servidor MySQL: 8.0.36

Bases de datos disponibles:
1. clientes
2. equipos
3. servicios

Seleccione una base de datos (número): 2
Generando dump de 'equipos'...
Dump exitoso: dumps/equipos_2025-06-14_20-36-08_dump.sql
```

---

## 🚀 Mejoras futuras

- Parámetro opcional para dump de **estructura solamente** o **datos solamente**
- Compresión automática con `gzip`
- Interfaz gráfica (Swing / JavaFX)
- Implementación como una utilidad Maven o CLI con Picocli

---

## 📝 Licencia

MIT License - © 2025 Santiago Da Silva

---

## 🛠 Autor

Desarrollado por Santiago Da Silva  
Contribuciones bienvenidas en pull requests o issues 👇
