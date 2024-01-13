/*  2 - Teste de API - utilize https://petstore.swagger.com
    2.1 - Cadastre o gato "Bichento"
    2.2 - Cadastre a usuária "Hermione Granger"
    2.3 - Venda o "Bichento" para a "Hermione Granger"
    2.4 - Consulte o status do animal após a venda
    2.5 - Consulte a ordem de venda do animal
 */
// 1 - Pacote
package teestPet;
// 2 - Biblioteca

import com.google.gson.Gson;
import entities.AccountEntity;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


// 3 - Classe
public class Account {
    // 3.1 - Atributos

    String ct = "application/json";

    String uri  = "https://petstore.swagger.io/v2/";
    Gson gson = new Gson();

    // Funções de Apoio
    public String lerJson(String caminhoJson) throws IOException {

        return new String(Files.readAllBytes(Paths.get(caminhoJson))) ;

    }


    // 3.2 - Metodos e Funcoes
    @Test
    public void testIncluirPet() throws IOException {
        // Configura

        String jsonBody = lerJson("src/test/resources/date/pet.json");
        given()
                .log().all()
                .contentType(ct)
                .body(jsonBody)
        .when()
                .post(uri + "pet")

        .then()
                .log().all()
                .statusCode(200)
                .body("id",is(1505))
                .body("category.name", is("Gato"))
                .body("name",is("Bichento"))
                .body("status",is("Disponivel"))
        ;
        System.out.print("Pet cadastrado no sistema");
    }

    @Test
    public void testIncluirUser() throws IOException {
        String jsonBody = lerJson("src/test/resources/date/user.json");
        given()
                .log().all()
                .contentType(ct)
                .body(jsonBody)
        .when()
                .post(uri + "user/createWithList")

        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("ok"))

        ;
        System.out.print("User cadastrado no sistema");
    }

    @Test
    public void testConsultaPet(){
        String petId = "1505";

        given()
                .log().all()
                .contentType(ct)

        .when()
                .get(uri + "pet/" + petId)

        .then()
                .log().all()
                .statusCode(200)
                .body("id",is(1505))
                .body("category.name", is("Gato"))
                .body("name",is("Bichento"))


        ;

    }


}
