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

import br.uff.midiacom.ana.connector.NCLCausalConnector;
import br.uff.midiacom.ana.NCLDoc;
import br.uff.midiacom.ana.NCLElement;
import br.uff.midiacom.ana.NCLIdentifiableElement;
import br.uff.midiacom.ana.NCLInvalidIdentifierException;
import br.uff.midiacom.ana.NCLValues.NCLParamInstance;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;


/**
 * Esta classe define o elemento <i>link</i> da <i>Nested Context Language</i> (NCL).
 * Este elemento define os relacionamentos entre mídias de um documento NCL.
 * O elo especifica o relacionamento atraves do atributo <i>xconnector</i> e dos elementos <i>bind</i>.
 * O conector define a natureza da interação entre as mídias, através da especificação de condições para a ativação do elo e ações a serem executadas quando este é ativado.
 * O bind associa os participantes do relacionamento aos seus respectivos papéis. Existem ainda os elementos do tipo parâmetro, <i>linkParam</i>, que atribuem valor a um parâmetro
 * do conector.<br/>
 *
 * @see NCLParam
 * @see NCLBind
 *
 * @see <a href="http://www.dtv.org.br/download/pt-br/ABNTNBR15606-2_2007Vc3_2008.pdf">
 *          ABNT NBR 15606-2:2007</a>
 */
public class NCLLink<L extends NCLLink, P extends NCLParam, B extends NCLBind, C extends NCLCausalConnector> extends NCLIdentifiableElement implements Comparable<L>{

    private C xconnector;
    
    private Set<P> linkParams = new TreeSet<P>();
    private List<B> binds = new ArrayList<B>();
    

    /**
     * Construtor do elemento <i>link</i> da <i>Nested Context Language</i> (NCL).
     */
    public NCLLink() {}


    /**
     * Construtor do elemento <i>link</i> da <i>Nested Context Language</i> (NCL).
     *
     * @param reader
     *          elemento representando o leitor XML do parser SAX.
     * @param parent
     *          elemento NCL representando o elemento pai.
     */
    public NCLLink(XMLReader reader, NCLElement parent) {
        setReader(reader);
        setParent(parent);

        getReader().setContentHandler(this);
    }


    /**
     * Atribui um conector ao link. Ele será responsável por definir a forma do relacionamento, isto é, as condições que deverão ser satisfeitas e as
     * ações que serão executadas caso as condições sejam satisfeita.
     *
     * @param xconnector
     *          Objeto representando o conector a ser atribuido ao link.
     */
    public void setXconnector(C xconnector) {
        this.xconnector = xconnector;
    }
    
    
    /**
     * Retorna o conector do link.
     *
     * @return
     *          Objeto representando o conector associado ao link.
     */
    public C getXconnector() {
        return xconnector;
    }
    
    
    /**
     * Adiciona um parâmetro ao link. Este parâmetro representa um par atributo-valor que informa ao elo uma característica adicional do relacionamento.
     *
     * @param param
     *          elemento representando o parâmetro a ser adicionado.
     * @return
     *          verdadeiro se o parâmetro foi adicionado.
     *
     * @see TreeSet#add
     */
    public boolean addLinkParam(P param) {
        if(linkParams.add(param)){
            //Se param existe, atribui este como seu parente
            if(param != null)
                param.setParent(this);

            return true;
        }
        return false;
    }
    
    
    /**
     * Remove um parâmetro do link.
     *
     * @param param
     *          elemento representando o parâmetro a ser removido.
     * @return
     *          verdadeiro se o parâmetro foi removido.
     *
     * @see TreeSet#remove
     */
    public boolean removeLinkParam(P param) {
        if(linkParams.remove(param)){
            //Se param existe, retira o seu parentesco
            if(param != null)
                param.setParent(null);

            return true;
        }
        return false;
    }
    
    
    /**
     * Verifica se o link possui um parâmetro.
     *
     * @param param
     *          elemento representando o parâmetro a ser verificado.
     * @return
     *          verdadeiro se o parâmetro existir.
     */
    public boolean hasLinkParam(P param) {
        return linkParams.contains(param);
    }
    
    
    /**
     * Verifica se o link possui algum parâmetro.
     *
     * @return
     *          verdadeiro se o link possui algum parâmetro.
     */
    public boolean hasLinkParam() {
        return !linkParams.isEmpty();
    }
    
    
    /**
     * Retorna os parâmetros do link.
     *
     * @return
     *          objeto Iterable contendo os parâmetros do link.
     */
    public Iterable<P> getLinkParams() {
        return linkParams;
    }
    
    
     /**
     * Adiciona um bind ao link. Este é o elemento responsável por fazer a associação
     * entre um papel do conector e a mídia que participará do relacionamento.
     *
     * @param bind
     *          elemento representando o bind a ser adicionado.
     * @return
     *          verdadeiro se o bind for adicionado.
     *
     * @see TreeSet#add
     */
    public boolean addBind(B bind) {
        if(bind != null && binds.add(bind)){
            //atribui este como parente do bind
            bind.setParent(this);

            return true;
        }
        return false;
    }
    
    
    /**
     * Remove um bind do link.
     *
     * @param bind
     *          elemento representando o bind a ser removido.
     * @return
     *          verdadeiro se o bind for removido.
     *
     * @see TreeSet#remove
     */
    public boolean removeBind(B bind) {
        if(binds.remove(bind)){
            //Se bind existe, retira o seu parentesco
            if(bind != null)
                bind.setParent(null);

            return true;
        }
        return false;
    }
    
    
    /**
     * Verifica se o link possui um bind.
     *
     * @param bind
     *          elemento representando o bind a ser verificado.
     * @return
     *          verdadeiro se o bind existir.
     */
    public boolean hasBind(B bind) {
        return binds.contains(bind);
    }


    /**
     * Verifica se o link possui algum bind.
     *
     * @return
     *          verdadeiro se o link possuir algum bind.
     */
    public boolean hasBind() {
        return !binds.isEmpty();
    }
    
    
    /**
     * Retorna os binds do link
     * 
     * @return
     *          objeto Iterable contendo os binds do link.
     */
    public Iterable<B> getBinds() {
        return binds;
    }
    
    
    public String parse(int ident) {
        String space, content;

        if(ident < 0)
            ident = 0;

        // Element indentation
        space = "";
        for(int i = 0; i < ident; i++)
            space += "\t";
        
        
        // <link> element and attributes declaration
        content = space + "<link";
        if(getId() != null)
            content += " id='" + getId() + "'";
        if(getXconnector() != null)
            content += " xconnector='" + getXconnector().getId() + "'";
        content += ">\n";
        
        // <link> element content
        if(hasLinkParam()){
            for(P param : linkParams)
                content += param.parse(ident + 1);
        }
        if(hasBind()){
            for(B bind : binds)
                content += bind.parse(ident + 1);
        }

        // <link> element end declaration
        content += space + "</link>\n";
        
        return content;
    }

    /**
     * Verifica se um um link qualquer é igual ao link atual.
     *
     * @param other
     *      Link a que se deseja comparar o link atual
     * @return
     *  0, caso sejam iguais
     *  !0, caso sejam diferentes
     */
    public int compareTo(L other) {
        int comp = 0;

        // Compara pelo xconnector
        if(getXconnector() != null)
            comp = getXconnector().compareTo(other.getXconnector());
        else
            comp = 1;

        // Compara o número de parâmetros
        if(comp == 0)
            comp = linkParams.size() - ((Set) other.getLinkParams()).size();

        // Compara o número de binds
        if(comp == 0)
            comp = binds.size() - ((Set) other.getBinds()).size();

        // Compara os parâmetros
        if(comp == 0){
            Iterator it = other.getLinkParams().iterator();
            for(P param : linkParams){
                P other_param = (P) it.next();
                comp = param.compareTo(other_param);
                if(comp != 0)
                    break;
            }
        }

        // Compara os binds
        if(comp == 0){
            Iterator it = other.getBinds().iterator();
            for(B bind : binds){
                B other_bind = (B) it.next();
                comp = bind.compareTo(other_bind);
                if(comp != 0)
                    break;
            }
        }


        return comp;
    }


    public boolean validate() {
        cleanWarnings();
        cleanErrors();

        boolean valid = true;

        if(getXconnector() == null){
            addError("Elemento não possui atriuto obrigatório xconnector.");
            valid = false;
        }
        if(binds.size() < 2){
            addError("Elemento não possui elementos filhos em cardinalidade correta. Deve possuir pelo menos dois binds.");
            valid = false;
        }

        if(hasLinkParam()){
            for(P param : linkParams){
                if(!param.getType().equals(NCLParamInstance.LINKPARAM)){
                    addError("Link não pode possuir parâmetros que não sejam linkParam.");
                    valid = false;
                }
                valid &= param.validate();
                addWarning(param.getWarnings());
                addError(param.getErrors());
            }
        }
        if(hasBind()){
            for(B bind : binds){
                valid &= bind.validate();
                addWarning(bind.getWarnings());
                addError(bind.getErrors());
            }
        }

        return valid;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try{
            if(localName.equals("link")){
                cleanWarnings();
                cleanErrors();
                for(int i = 0; i < attributes.getLength(); i++){
                    if(attributes.getLocalName(i).equals("id"))
                        setId(attributes.getValue(i));
                    else if(attributes.getLocalName(i).equals("xconnector"))
                        setXconnector((C) new NCLCausalConnector(attributes.getValue(i)));//cast retirado na correcao das referencias
                }
            }
            else if(localName.equals("linkParam")){
                P child = createLinkParam();
                child.startElement(uri, localName, qName, attributes);
                addLinkParam(child);
            }
            else if(localName.equals("bind")){
                B child = createBind();
                child.startElement(uri, localName, qName, attributes);
                addBind(child);
            }
        }
        catch(NCLInvalidIdentifierException ex){
            addError(ex.getMessage());
        }
    }


    @Override
    public void endDocument() {
        if(getParent() != null){
            if(getXconnector() != null)
                connectorReference();
        }

        if(hasLinkParam()){
            for(P param : linkParams){
                param.endDocument();
                addWarning(param.getWarnings());
                addError(param.getErrors());
            }
        }
        if(hasBind()){
            for(B bind : binds){
                bind.endDocument();
                addWarning(bind.getWarnings());
                addError(bind.getErrors());
            }
        }
    }

    /**
     * Retorna os conectores existentes na base de conectores do documento.
     * @return
     *  objeto Iterable contendo os conectores.
     */
    private Iterable<C> getConnectors() {
        NCLElement root = getParent();

        while(!(root instanceof NCLDoc)){
            root = root.getParent();
            if(root == null){
                addWarning("Could not find a root element");
                return null;
            }
        }

        if(((NCLDoc) root).getHead() == null){
            addWarning("Could not find a head");
            return null;
        }
        if(((NCLDoc) root).getHead().getConnectorBase() == null){
            addWarning("Could not find a connectorBase");
            return null;
        }

        return ((NCLDoc) root).getHead().getConnectorBase().getCausalConnectors();
    }

    /**
     * Verifica se o conector associado ao elo existe na base de conectores do
     * documento. Caso nãop exista, adiciona uma advertencias a lista de advertencias.
     */
    private void connectorReference() {
        //Search for the connector inside the base
        Iterable<C> connectors = getConnectors();
        if(connectors == null){
            addWarning("Could not find connector in connectorBase with id: " + getXconnector().getId());
            return;
        }

        for(C connector : connectors){
            if(connector.getId().equals(getXconnector().getId())){
                setXconnector(connector);
                return;
            }
        }

        addWarning("Could not find connector in connectorBase with id: " + getXconnector().getId());
    }


    /**
     * Função de criação do elemento filho <i>linkParam</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>linkParam</i>.
     */
    protected P createLinkParam() {
        return (P) new NCLParam(NCLParamInstance.LINKPARAM, getReader(), this);
    }


    /**
     * Função de criação do elemento filho <i>bind</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>bind</i>.
     */
    protected B createBind() {
        return (B) new NCLBind(getReader(), this);
    }
}
