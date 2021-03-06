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
package br.uff.midiacom.ana.link;

import br.uff.midiacom.ana.connector.NCLConnectorParam;
import br.uff.midiacom.ana.NCLElement;
import br.uff.midiacom.ana.NCLInvalidIdentifierException;
import br.uff.midiacom.ana.NCLValues.NCLParamInstance;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;


/**
 * Esta classe define um parâmetro interno a um elemento <i>link</i> ou <i>bind</i>
 * da <i>Nested Context Language</i> (NCL). Possui dois atributos, <i>name</i> e <i>value</i>. O atributo name representa o nome do parâmetro
 * e faz referência ao nome do parâmetro do conector, definido previamente pelo elemento <i>connectorParam</i>. O atributo value recebe o valor do parâmetro em questão.
 * Caso o parâmetro seja definido como um parâmetro do elo (linkParam), seu valor é válido para qualquer bind pertencente ao elo. Caso seja definido como parâmetro
 * do bind (bindParam), seu valor é válido apenas para o bind em que ele foi definido.<br/>
 *
 * @see NCLConnectorParam
 *
 * @see <a href="http://www.dtv.org.br/download/pt-br/ABNTNBR15606-2_2007Vc3_2008.pdf">
 *          ABNT NBR 15606-2:2007</a>
 */
public class NCLParam<P extends NCLParam, C extends NCLConnectorParam> extends NCLElement implements Comparable<P>{

    private C name;
    private String value;

    private NCLParamInstance paramType;
    
    
    /**
     * Construtor do parâmetro interno a um elemento <i>link</i> ou <i>bind</i>.
     *
     * @param paramType
     *          define se o parâmetro é de um elemento <i>linkParam</i> ou <i>bindParam</i>.
     *
     * @throws java.lang.NullPointerException
     *          se o parâmetro for nulo.
     */
    public NCLParam(NCLParamInstance paramType) throws NullPointerException {
        if(paramType == null)
            throw new NullPointerException("Null type");

        this.paramType = paramType;
    }


    /**
     * Construtor do parâmetro interno a um elemento <i>link</i> ou <i>bind</i>.
     *
     * @param paramType
     *          define se o parâmetro é de um elemento <i>link</i> ou <i>bind</i>.
     * @param reader
     *          elemento representando o leitor XML do parser SAX.
     * @param parent
     *          elemento NCL representando o elemento pai.
     *
     * @throws java.lang.NullPointerException
     *          se o tipo for nulo.
     */
    public NCLParam(NCLParamInstance paramType, XMLReader reader, NCLElement parent) throws NullPointerException {
        if(paramType == null)
            throw new NullPointerException("Null type");

        this.paramType = paramType;
        setReader(reader);
        setParent(parent);

        getReader().setContentHandler(this);
    }
    
    
    /**
     * Associa o parâmetro, relacionado ao elo, a um parâmetro definido pelo conector (<i>connectorParam</i>(.
     *
     * @param connectorParam
     *          Objeto representando o parâmetro do conector ao qual este parâmetro se refere.
     */
    public void setName(C connectorParam) {
        this.name = connectorParam;
    }
    
    /**
     * Retorna o <i>connectorParam</i> ao qual o parâmetro se refere.
     *
     * @return NCLConnectorParam representando o nome do parâmetro.
     */
    public C getName() {
        return name;
    }
    
    
   /**
     * Determina o valor do atributo value do parâmetro.
     *
     * @param value
     *          String contendo o valor a ser atribuído ao parâmetro.
     *
     * @throws java.lang.IllegalArgumentException
     *          Se o valor a ser atribuído for uma String vazia.
     */
    public void setValue(String value)  throws IllegalArgumentException {
        if(value != null && "".equals(value.trim()))
            throw new IllegalArgumentException("Empty value String");

        this.value = value;
    }
    
    /**
     * Retorna o valor do atributo value do parâmetro.
     *
     * @return
     *          String contendo o valor atribuído ao parâmetro.
     */
    public String getValue() {
        return value;
    }

    /**
    * Retorna o tipo do parâmetro, se um parâmetro do elo (linkParam) ou da ligação (bindParam).
    * @return
    *      Objeto do tipo <i>NCLParamInstance</i> representando o tipo do parâmetro.
    */
    public NCLParamInstance getType() {
        return paramType;
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
        content = space + "<" + paramType.toString();
        if(getName() != null)
            content += " name='" + getName().getName() + "'";
        if(getValue() != null)
            content += " value='" + getValue() + "'";
        content += "/>\n";
        
        return content;
    }
    
    /**
     * Compara o parâmetro atual a um outro parâmetro qualquer.
     * @param other
     * parâmetro ao qual se deseja comparar o atual.
     * @return
     * 0, se forem iguais
     * 1 ou -1, caso sejam diferentes
     */
    public int compareTo(P other) {
        return getName().compareTo(other.getName());
    }


    public boolean validate() {
        cleanWarnings();
        cleanErrors();

        boolean valid = true;

        if(getName() == null){
            addError("Elemento não possui atributo obrigatório name.");
            valid = false;
        }
        if(getValue() == null){
            addError("Elemento não possui atributo obrigatório value.");
            valid = false;
        }

        if(getParent() instanceof NCLLink && ((NCLLink) getParent()).getXconnector() != null && !((NCLLink) getParent()).getXconnector().hasConnectorParam(getName().getName())){
            addError("Atributo name deve referênciar um parâmetro especificado pelo conector utilizado no link.");
            valid = false;
        }
        else if(getParent().getParent() != null && ((NCLLink) getParent().getParent()).getXconnector() != null && !((NCLLink) getParent().getParent()).getXconnector().hasConnectorParam(getName().getName())){
            addError("Atributo name deve referênciar um parâmetro especificado pelo conector utilizado no link.");
            valid = false;
        }

        return valid;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try{
            cleanWarnings();
            cleanErrors();
            for(int i = 0; i < attributes.getLength(); i++){
                if(attributes.getLocalName(i).equals("name"))
                    setName((C) new NCLConnectorParam(attributes.getValue(i)));//cast retirado na correcao das referencias
                else if(attributes.getLocalName(i).equals("value"))
                    setValue(attributes.getValue(i));
            }
        }
        catch(NCLInvalidIdentifierException ex){
            addError(ex.getMessage());
        }
    }


    @Override
    public void endDocument() {
        if(getParent() == null)
            return;

        if(getName() != null)
            nameReference();
    }

    /**
     * Verifica se o parâmetro está definido ou no link, ou no conector, segundo o seu tipo.
     * Caso não exista o parametro, o link ou o conector uma advertencia é adicionada
     * a lista de advertencias.
     */
    private void nameReference() {
        //Search for the connector parameter inside the connector
        NCLElement link = getParent();

        while(!(link instanceof NCLLink)){
            link = link.getParent();{
                if(link == null){
                    addWarning("Could not find a parent link");
                    return;
                }
            }
        }

        if(((NCLLink) link).getXconnector() == null){
            addWarning("Could not find a connector");
            return;
        }

        Iterable<C> params = ((NCLLink) link).getXconnector().getConnectorParams();

        for(C param : params){
            if(param.getName().equals(getName().getName())){
                setName(param);
                return;
            }
        }

        addWarning("Could not find connectorParam in connector with name: " + getName().getName());
    }
}
