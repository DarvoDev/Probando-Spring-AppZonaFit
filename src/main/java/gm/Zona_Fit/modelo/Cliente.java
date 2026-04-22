package gm.Zona_Fit.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data //genera get y set de todos los atributos de nuestra clase
@NoArgsConstructor //Constructor vacio
@AllArgsConstructor //constructor con todos los argumentos
@ToString //agrega toString
@EqualsAndHashCode
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PRIMARY KEY
    private Integer id; //su valor por defecto es null, en int es 0
    private String nombre;
    private String apellido;
    private Integer membresia;
}
