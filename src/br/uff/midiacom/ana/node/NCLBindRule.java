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
package br.uff.midiacom.ana.node;

import br.uff.midiacom.ana.NCLDoc;
import br.uff.midiacom.ana.NCLElement;
import br.uff.midiacom.ana.NCLInvalidIdentifierException;
import br.uff.midiacom.ana.rule.NCLRule;
import br.uff.midiacom.ana.rule.NCLTestRule;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;


/**
 * Esta classe define o elemento <i>bindRule</i> de um switch da <i>Nested Context Language</i> (NCL).
 * Este elemento  define um bind de um switch de um documento NCL. Ele faz a associação entre uma regra e o nó que será executado caso a regra
 * seja avaliada como verdadeira. Possui os atributos: <i>rule</i>, que define a regra a ser avaliada;
 * <i>constituent</i>, que define o nó relacionado a regra.<br/>
 *
 * @see <a href="http://www.dtv.org.br/download/pt-br/ABNTNBR15606-2_2007Vc3_2008.pdf">
 *          ABNT NBR 15606-2:2007</a>
 */
public class NCLBindRule<B extends NCLBindRule, N extends NCLNode, R extends NCLTestRule> extends NCLElement implements Comparable<B> {

    private N constituent;
    private R rule;


    /**
     * Construtor do elemento <i>bindRule</i> da <i>Nested Context Language</i> (NCL).
     */
    public NCLBindRule() {}


    /**
     * Construtor do elemento <i>bindRule</i> da <i>Nested Context Language</i> (NCL).
     *
     * @param reader
     *          elemento representando o leitor XML do parser SAX.
     * @param parent
     *          elemento NCL representando o elemento pai.
     */
    public NCLBindRule(XMLReader reader, NCLElement parent) {
        setReader(reader);
        setParent(parent);

        getReader().setContentHandler(this);
    }

    
    /**
     * Atribui um constituent ao bind.
     *
     * @param constituent
     *          elemento representando o nó mapeado pelo bind.
     */
    public void setConstituent(N constituent) {
        this.constituent = constituent;
    }


    /**
     * Retorna o constituent do bind.
     *
     * @return
     *          elemento representando o nó mapeado pelo bind.
     */
    public N getConstituent() {
        return constituent;
    }


    /**
     * Atribui uma regra de avaliação ao bind.
     *
     * @param rule
     *          elemento representando a regra de avaliação do bind.
     */
    public void setRule(R rule) {
        this.rule = rule;
    }


    /**
     * Retorna a regra de avaliação do bind.
     *
     * @return
     *          elemento representando a regra de avaliação do bind.
     */
    public R getRule() {
        return rule;
    }


    public String parse(int ident) {
        String space, content;

        if(ident < 0)
            ident = 0;

        // Element indentation
        space = "";
        for(int i = 0; i < ident; i++)
            space += "\t";

        content = space + "<bindRule";
        if(getRule() != null)
            content += " rule='" + getRule().getId() + "'";
        if(getConstituent() != null)
            content += " constituent='" + getConstituent().getId() + "'";
        content += "/>\n";


        return content;
    }

    /**
     * Compara o bindRule atual a um outro elemento bindRule. O método verifica se
     * os dois bindRules possuem os mesmos atributos <i>rule</i> e <i>constituent</i>.
     *
     * @param other
     *      BindRule que se deseja comparar ao bindRule atual.
     * @return
     *      Retorna 0 caso os bindRules sejam iguais.
     *      Retorna -1 caso sejam diferentes.
     */
    public int compareTo(B other) {
        int comp = 0;

        // Compara pela regra
        if(getRule() != null)
            comp = getRule().compareTo(other.getRule());
        else
            comp = -1;

        // Compara pelo constituent
        if(comp == 0){
            if(getConstituent() != null)
                comp = getConstituent().compareTo(other.getConstituent());
            else
                comp = -1;
        }

        return comp;
    }


    public boolean validate() {
        cleanWarnings();
        cleanErrors();

        boolean valid = true;

        if(getRule() == null){
            addError("Elemento não possui atributo obrigatório rule.");
            valid = false;
        }
        if(getConstituent() == null){
            addError("Elemento não possui atributo obrigatório constituent.");
            valid = false;
        }

        if(getConstituent() != null && !((NCLSwitch) getParent()).hasNode(getConstituent())){
            addError("Atributo constituent deve fazer referência a um descritor contido no switch.");
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
                if(attributes.getLocalName(i).equals("rule"))
                    setRule((R) new NCLRule(attributes.getValue(i)));//cast retirado na correcao das referencias
                else if(attributes.getLocalName(i).equals("constituent"))
                    setConstituent((N) new NCLContext(attributes.getValue(i)));//cast retirado na correcao das referencias
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

        if(getConstituent() != null)
            constituentReference();

        if(getRule() != null)
            ruleReference();
    }

    /**
     * Retorna o conjunto de regras contido na base de regras do documento.
     * @return
     *      objeto Iterable contendo as regras da base.
     */
    private Iterable<R> getRules() {
        NCLElement root = getParent();

        while(!(root instanceof NCLDoc)){
            root = root.getParent();
            if(root == null){
                addWarning("Could not find root element");
                return null;
            }
        }

        if(((NCLDoc) root).getHead() == null){
            addWarning("Could not find a head");
            return null;
        }
        if(((NCLDoc) root).getHead().getRuleBase() == null){
            addWarning("Could not find a ruleBase");
            return null;
        }

        return ((NCLDoc) root).getHead().getRuleBase().getRules();
    }

    /**
     * Verifica se o nó associado ao elemento constituent existe dentro do elemento
     * switch ao qual pertence o bindRole atual. Caso não exista, adiciona uma advertência
     * a lista de advertências.
     */
    private void constituentReference() {
        //Search for a component node in its parent
        Iterable<N> nodes = ((NCLSwitch) getParent()).getNodes();

        for(N node : nodes){
            if(node.getId().equals(getConstituent().getId())){
                setConstituent(node);
                return;
            }
        }

        addWarning("Could not find node in switch with id: " + getConstituent().getId());
    }

    /**
     * Verifica se a regra associada ao bindRule existe na base de regras do documento.
     * Caso não exista, adiciona uma advertência a lista de advertências.
     */
    private void ruleReference() {
        //Search for the interface inside the node
        Iterable<R> rules = getRules();
        if(rules == null)
            return;
        
        for(R rul : rules){
            if(rul.getId().equals(getRule().getId())){
                setRule(rul);
                return;
            }
        }
        //@todo: regras internas a regras compostas podem ser utilizadas?

        addWarning("Could not find rule in ruleBase with id: " + getRule().getId());
    }
}
