package br.com.mytasks.validation;


import br.com.mytasks.entities.Category;
import br.com.mytasks.exception.InvalidArgumentsException;

public class Validation {


    /**
     * Método responsável por validar se uma string é válida, ou seja,
     * se ela não é nula ou vazia.
     * @param nome
     *      String a ser validada.
     */
    public static void nameValidation(String nome) throws InvalidArgumentsException {
        if(nome == null || nome.equals("") ) {
            throw new InvalidArgumentsException("Invalid name.");
        }

    }

    /**
     * Método responsável por validar se a id inserida é válida, ou seja,
     * se não é igual a zero
     *
     * @param id
     *         id a ser validada.
     */
    public static void idValidation(long id) throws  InvalidArgumentsException{
        if (id == 0){
            throw new InvalidArgumentsException("Invalid id.");
        }
    }

    /**
     * Verifica se uma categoria é válida, ou seja, não 'aponta' para null.
     *
     * @param category
     *          Categoria a ser validada
     *
     * @throws InvalidArgumentsException
     */
    public static void validateCategory(Category category) throws InvalidArgumentsException {
        if(category == null){
            throw new InvalidArgumentsException("Invalid category.");
        }
    }
}