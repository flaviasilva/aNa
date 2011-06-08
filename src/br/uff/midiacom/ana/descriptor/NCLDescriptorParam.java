/********************************************************************************
 * This file is part of the api for NCL authoring - aNa.
 *
 * Copyright (c) 2011, MídiaCom Lab (www.midiacom.uff.br)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement:
 *        This product includes the Api for NCL Authoring - aNa
 *        (http://joeldossantos.github.com/aNa).
 *
 *  * Neither the name of the lab nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without specific
 *    prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY MÍDIACOM LAB AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE MÍDIACOM LAB OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *******************************************************************************/
package br.uff.midiacom.ana.descriptor;

import br.uff.midiacom.ana.NCLElement;
import br.uff.midiacom.ana.NCLValues.NCLAttributes;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;


/**
 * Esta classe define o elemento <i>descriptorParam</i> da <i>Nested Context Language</i> (NCL).
 * Este elemento é o elemento que define um parametro de um descritor como um par name (nome da propriedade do elemento descriporParam) e value(valor a ela associado).
 * Cada descritor pode conter diversos elementos <i>descriptorParam</i>.<br/>
 *
 * @see <a href="http://www.dtv.org.br/download/pt-br/ABNTNBR15606-2_2007Vc3_2008.pdf">
 *          ABNT NBR 15606-2:2007</a>
 */
public class NCLDescriptorParam<P extends NCLDescriptorParam> extends NCLElement implements Comparable<P> {

    private NCLAttributes name;
    private String value;


    /**
     * Construtor do elemento <i>descriptorParam</i> da <i>Nested Context Language</i> (NCL).
     */
    public NCLDescriptorParam() {}


    /**
     * Construtor do elemento <i>descriptorParam</i> da <i>Nested Context Language</i> (NCL).
     *
     * @param reader
     *          elemento representando o leitor XML do parser SAX.
     * @param parent
     *          elemento NCL representando o elemento pai.
     */
    public NCLDescriptorParam(XMLReader reader, NCLElement parent) {
        setReader(reader);
        setParent(parent);

        getReader().setContentHandler(this);
    }


    /**
     * Atribui um nome a propriedade do parâmetro, dentre as propriedades padronizadas do NCL.
     *
     * @param name
     *          Elemento representando o nome do parâmetro.
     */
    public void setName(NCLAttributes name) {
        this.name = name;
    }


    /**
     * Retorna o nome da propriedade do parâmetro.
     *
     * @return
     *          elemento representando o nome do parâmetro.
     */
    public NCLAttributes getName() {
        return name;
    }


    /**
     * Atribui um valor ao atributo value do parâmetro.
     *
     * @param value
     *          String representando o valor do parâmetro.
     * @throws IllegalArgumentException
     *          Dispara uma exceção caso a String representando o valor seja vazia.
     */
    public void setValue(String value) throws IllegalArgumentException {
        if(value != null && "".equals(value.trim()))
            throw new IllegalArgumentException("Empty value String");

        this.value = value;
    }


    /**
     * Retorna uma string representando o valor atribuido ao atributo value do parâmetro.
     *
     * @return
     *          String representando o valor do parâmetro.
     */
    public String getValue() {
        return value;
    }


    public String parse(int ident) {
        String space, content;

        if(ident < 0)
            ident = 0;

        // Element indentation
        space = "";
        for(int i = 0; i < ident; i++)
            space += "\t";


        // param element and attributes declaration
        content = space + "<descriptorParam";
        if(getName() != null)
            content += " name='" + getName().toString() + "'";
        if(getValue() != null)
            content += " value='" + getValue() + "'";
        content += "/>\n";

        return content;
    }

    /**
     * Compara o DescriptorParam atual a um outro objeto DescriptorParam, através
     * do valor do atributo name.
     * @param other
     *      DescriptorParam que será comparado ao atual.
     * @return
     *      0, se os parâmetros forem iguais.
     *      1 ou -1, se os parâmetros forem diferentes.
     */
    public int compareTo(P other) {
        return getName().compareTo(other.getName());
    }


    public boolean validate() {
        cleanWarnings();
        cleanErrors();

        boolean valid = true;

        if(getName() == null){
            addError("Elemento não possui atributo obrigatório name");
            valid = false;
        }
        if(getValue() == null){
            addError("Elemento não possui atributo obrigatório value");
            valid = false;
        }
        //@todo: validar o valor com relacao ao nome

        return valid;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        cleanWarnings();
        cleanErrors();
        for(int i = 0; i < attributes.getLength(); i++){
            if(attributes.getLocalName(i).equals("name")){
                    for(NCLAttributes a : NCLAttributes.values()){
                        if(a.toString().equals(attributes.getValue(i)))
                            setName(a);
                    }
                }
            else if(attributes.getLocalName(i).equals("value"))
                setValue(attributes.getValue(i));
        }
    }
}
