package herencia; //vas a guardar todo en  herencia

//DEFINICION CLASE PERSONA
public class Persona
{ //clase Persona
	private String nombre;
	private String apellido;
	private int edad;

	//CONSTRUCTOR DE PERSONA
public Persona (String nombre, String apellido, int edad)
{
	this.nombre = nombre; //INICIALIZAR nombre
	this.apellido = apellido; //INICIALIZAR apellido
	this.edad=edad; //INICIALIZAR edad
}
//METODOS operaciones
public String getNombre()
{ //obtener el nombre
	return nombre; //devolver el nombre
}

public String getApellido()
{ //obtener el apellido
	return apellido; //devolver el apellido
}
public int getEdad()
{ //obtener la edad
	return edad;   //devolver la edad
}

}
