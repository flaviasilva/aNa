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

import br.uff.midiacom.ana.interfaces.NCLPort;
import br.uff.midiacom.ana.interfaces.NCLProperty;
import br.uff.midiacom.ana.link.NCLLink;
import br.uff.midiacom.ana.meta.NCLMeta;
import br.uff.midiacom.ana.meta.NCLMetadata;
import br.uff.midiacom.ana.NCLBody;
import br.uff.midiacom.ana.NCLElement;
import br.uff.midiacom.ana.NCLIdentifiableElement;
import br.uff.midiacom.ana.NCLInvalidIdentifierException;
import java.util.Set;
import java.util.TreeSet;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;


/**
 * Esta classe define o elemento <i>context</i> da <i>Nested Context Language</i> (NCL).
 * Este elemento é o elemento que define um contexto de um documento NCL. Um contexto encapsula os objetos e os elos que contém, favorecendo uma estruturação mais independente do documento e o reúso das  mídias
 * e relacionamentos contidos nele. O elemento <i>body</i> é um caso particular de contexto, que representa a aplicação como um todo.
 * Um contexto pode aninhar outros contextos ou switches, mas não pode conter recursivamente a si mesmo. Contextos também podem ser reutilizados, quando um contexto referencia outros através do elemento refer.<br/>
 *
 * @see <a href="http://www.dtv.org.br/download/pt-br/ABNTNBR15606-2_2007Vc3_2008.pdf">
 *          ABNT NBR 15606-2:2007</a>
 */
public class NCLContext<C extends NCLContext, Pt extends NCLPort, Pp extends NCLProperty, N extends NCLNode, L extends NCLLink, M extends NCLMeta, MT extends NCLMetadata>
        extends NCLIdentifiableElement implements NCLNode<N> {

    private C refer;
    private Set<Pt> ports = new TreeSet<Pt>();
    private Set<Pp> properties = new TreeSet<Pp>();
    private Set<N> nodes = new TreeSet<N>();
    private Set<L> links = new TreeSet<L>();
    private Set<M> metas = new TreeSet<M>();
    private Set<MT> metadatas = new TreeSet<MT>();

    private boolean insideContext;
    
    
    /**
     * Construtor do elemento <i>context</i> da <i>Nested Context Language</i> (NCL).
     * 
     * @param id
     *          identificador do contexto.
     * @throws br.pensario.NCLInvalidIdentifierException
     *          se o identificador do contexto for inválido.
     */
    public NCLContext(String id) throws NCLInvalidIdentifierException {
        setId(id);
    }


    /**
     * Construtor do elemento <i>context</i> da <i>Nested Context Language</i> (NCL).
     *
     * @param reader
     *          elemento representando o leitor XML do parser SAX.
     * @param parent
     *          elemento NCL representando o elemento pai.
     */
    public NCLContext(XMLReader reader, NCLElement parent) {
        setReader(reader);
        setParent(parent);

        getReader().setContentHandler(this);

        insideContext = false;
    }


    /**
     * Atribui um contexto ao atributo refer, para ser reutilizado pelo contexto atual.
     *
     * @param refer
     *          elemento representando o contexto a ser reutilizado.
     */
    public void setRefer(C refer) {
        this.refer = refer;
    }


    /**
     * Retorna o contexto associado ao atributo refer do contexto atual.     *Retorna a mídia associada ao atributo refer da mídia atual.
     *
     * @return
     *          elemento representando o contexto a ser reutilizado.
     */
    public C getRefer() {
        return refer;
    }
    
    
    /**
     * Adiciona uma porta ao contexto.
     *
     * @param port
     *          elemento representando a porta a ser adicionada.
     * @return
     *          Verdadeiro se a porta foi adicionada.
     *
     * @see TreeSet#add
     */
    public boolean addPort(Pt port) {
        if(ports.add(port)){
            //Se port existe, atribui este como seu parente
            if(port != null)
                port.setParent(this);

            return true;
        }
        return false;
    }


    /**
     * Remove uma porta do contexto a partir do seu identificador.
     *
     * @param id
     *          identificador da porta a ser removida.
     * @return
     *          Verdadeiro se a porta foi removida.
     *
     * @see TreeSet#remove
     */
    public boolean removePort(String id) {
        for(Pt port : ports){
            if(port.getId().equals(id))
                return removePort(port);
        }
        return false;
    }


    /**
     * Remove uma porta do contexto a partir de uma referência a porta.
     *
     * @param port
     *         Objeto representando a porta a ser removida.
     * @return
     *          Verdadeiro se a porta foi removida.
     *
     * @see TreeSet#remove
     */
    public boolean removePort(Pt port) {
        if(ports.remove(port)){
            //Se port existe, retira o seu parentesco
            if(port != null)
                port.setParent(null);

            return true;
        }
        return false;
    }


    /**
     * Verifica se o contexto possui uma porta. Faz a busca a partir do identificador.
     *
     * @param id
     *         string representando o identificador da porta a ser verificada.
     * @return
     *          verdadeiro se a porta existir.
     */
    public boolean hasPort(String id) {
        for(Pt port : ports){
            if(port.getId().equals(id))
                return true;
        }
        return false;
    }


    /**
     * Verifica se o contexto possui uma porta. faz a busca a partir de uma referência a porta.
     *
     * @param port
     *          Objeto representando a porta a ser verificada.
     * @return
     *          verdadeiro se a porta existir.
     */
    public boolean hasPort(Pt port) {
        return ports.contains(port);
    }


    /**
     * Verifica se o contexto possui alguma porta.
     *
     * @return
     *          verdadeiro se o contexto possuir alguma porta.
     */
    public boolean hasPort() {
        return !ports.isEmpty();
    }


    /**
     * Retorna as portas do contexto.
     *
     * @return
     *          objeto Iterable contendo as portas do contexto.
     */
    public Iterable<Pt> getPorts() {
        return ports;
    }


    /**
     * Adiciona uma propriedade ao contexto.
     *
     * @param property
     *          objeto representando a propriedade a ser adicionada.
     * @return
     *          Verdadeiro se a propriedade foi adicionada.
     *
     * @see TreeSet#add
     */
    public boolean addProperty(Pp property) {
        if(properties.add(property)){
            //Se property existe, atribui este como seu parente
            if(property != null)
                property.setParent(this);

            return true;
        }
        return false;
    }


    /**
     * Remove uma propriedade do contexto a partir do nome da propriedade.
     *
     * @param name
     *          string representando o nome da propriedade a ser removida.
     * @return
     *          Verdadeiro se a propriedade foi removida.
     *
     * @see TreeSet#remove
     */
    public boolean removeProperty(String name) {
        for(Pp property : properties){
            if(property.getId().equals(name))
                return removeProperty(property);
        }
        return false;
    }


    /**
     * Remove uma propriedade do contexto a partir de uma referencia a propriedade.
     *
     * @param property
     *          elemento representando a propriedade a ser removida.
     * @return
     *          Verdadeiro se a propriedade foi removida.
     *
     * @see TreeSet#remove
     */
    public boolean removeProperty(Pp property) {
        if(properties.remove(property)){
            //Se property existe, retira o seu parentesco
            if(property != null)
                property.setParent(null);

            return true;
        }
        return false;
    }


    /**
     * Verifica se o contexto possui uma propriedade. Busca pelo nome da propriedade.
     *
     * @param name
     *          String representando o nome da propriedade a ser verificada.
     * @return
     *          verdadeiro se a propriedade existir.
     */
    public boolean hasProperty(String name) {
        for(Pp property : properties){
            if(property.getId().equals(name))
                return true;
        }
        return false;
    }


    /**
     * Verifica se o contexto possui uma propriedade. Busca a partir de uma
     * referencia a propriedade a ser verificada.
     *
     * @param property
     *          elemento representando a propriedade a ser verificada.
     * @return
     *          verdadeiro se a propriedade existir.
     */
    public boolean hasProperty(Pp property) {
        return properties.contains(property);
    }


    /**
     * Verifica se o contexto possui alguma propriedade.
     *
     * @return
     *          verdadeiro se o contexto possuir alguma propriedade.
     */
    public boolean hasProperty() {
        return !properties.isEmpty();
    }


    /**
     * Retorna as propriedades do contexto.
     *
     * @return
     *          objeto Iterable contendo as propriedades do contexto.
     */
    public Iterable<Pp> getProperties() {
        return properties;
    }


    /**
     * Adiciona um nó ao contexto. Estes podem ser do tipo <i>media</i>, <i>switch</i>,
     * ou até mesmo um outro <i>context</i>.
     *
     * @param node
     *          elemento representando o nó a ser adicionado.
     * @return
     *          Verdadeiro se o nó foi adicionado.
     *
     * @see TreeSet#add
     */
    public boolean addNode(N node) {
        if(nodes.add(node)){
            //Se node existe, atribui este como seu parente
            if(node != null)
                node.setParent(this);

            return true;
        }
        return false;
    }


    /**
     * Remove um nó do contexto a partir do seu id.
     *
     * @param id
     *          identificador do nó a ser removido.
     * @return
     *          Verdadeiro se o nó foi removido.
     *
     * @see TreeSet#remove
     */
    public boolean removeNode(String id) {
        for(N node : nodes){
            if(node.getId().equals(id))
                return removeNode(node);
        }
        return false;
    }


   /**
     * Remove um nó do contexto a partir de uma referencia a ele.
     *
     * @param node
     *          elemento representando um nó a ser removido.
     * @return
     *          Verdadeiro se o nó foi removido.
     *
     * @see TreeSet#remove
     */
    public boolean removeNode(N node) {
        if(nodes.remove(node)){
            //Se node existe, retira o seu parentesco
            if(node != null)
                node.setParent(null);

            return true;
        }
        return false;
    }


    /**
     * Verifica se o contexto possui um nó. Faz a busca a partir do identificador do nó.
     *
     * @param id
     *          String repreentando o identificador do nó a ser verificado.
     * @return
     *          verdadeiro se o nó existir.
     */
    public boolean hasNode(String id) {
        for(N node : nodes){
            if(node.getId().equals(id))
                return true;
        }
        return false;
    }


    /**
     * Verifica se o contexto possui um nó. Faz a busca a partir de uma referência ao nó.
     *
     * @param node
     *          elemento representando o nó a ser verificado.
     * @return
     *          verdadeiro se o nó existir.
     */
    public boolean hasNode(N node) {
        return nodes.contains(node);
    }


    /**
     * Verifica se o contexto possui algum nó.
     *
     * @return
     *          verdadeiro se o contexto possuir algum nó.
     */
    public boolean hasNode() {
        return (!nodes.isEmpty());
    }


    /**
     * Retorna os nós do contexto.
     *
     * @return
     *          objeto Iterable contendo os nós do contexto.
     */
    public Iterable<N> getNodes() {
        return nodes;
    }


    /**
     * Adiciona um link ao contexto.
     *
     * @param link
     *          elemento representando o link a ser adicionado.
     * @return
     *          Verdadeiro se o link foi adicionado.
     *
     * @see TreeSet#add
     */
    public boolean addLink(L link) {
        if(links.add(link)){
            //Se link existe, atribui este como seu parente
            if(link != null)
                link.setParent(this);

            return true;
        }
        return false;
    }


     /**
     * Remove um link do contexto, a partir de uma referência ao link.
     *
     * @param link
     *          elemento representando o link a ser removido.
     * @return
     *          Verdadeiro se o link foi removido.
     *
     * @see TreeSet#remove
     */
    public boolean removeLink(L link) {
        if(links.remove(link)){
            //Se link existe, retira o seu parentesco
            if(link != null)
                link.setParent(null);

            return true;
        }
        return false;
    }


    /**
     * Verifica se o contexto possui um link. Busca a partir da referencia.
     *
     * @param link
     *          elemento representando o link a ser verificado.
     * @return
     *          verdadeiro se o link existir.
     */
    public boolean hasLink(L link) {
        return links.contains(link);
    }


    /**
     * Verifica se o contexto possui algum link.
     *
     * @return
     *          verdadeiro se o contexto possuir algum link.
     */
    public boolean hasLink() {
        return !links.isEmpty();
    }


     /**
     * Retorna os links do contexto.
     *
     * @return
     *          objeto Iterable contendo os links do contexto.
     */
    public Iterable<L> getLinks() {
        return links;
    }


    /**
     * Adiciona um metadado ao cabeçalho do documento NCL.
     *
     * @param meta
     *          elemento representando o metadado a ser adicionado.
     * @return
     *          Verdadeiro se o metadado foi adicionado.
     *
     * @see TreeSet#add
     */
    public boolean addMeta(M meta) {
        if(metas.add(meta)){
            //Se meta existe, atribui este como seu parente
            if(meta != null)
                meta.setParent(this);

            return true;
        }
        return false;
    }


    /**
     * Remove um metadado do cabeçalho do documento NCL.
     *
     * @param meta
     *          elemento representando o metadado a ser removido.
     * @return
     *          Verdadeiro se o link foi removido.
     *
     * @see TreeSet#remove
     */
    public boolean removeMeta(M meta) {
        if(metas.remove(meta)){
            //Se meta existe, retira o seu parentesco
            if(meta != null)
                meta.setParent(null);

            return true;
        }
        return false;
    }


    /**
     * Verifica se o cabeçalho do documento NCL possui um metadado.
     *
     * @param meta
     *          elemento representando o metadado a ser verificado.
     * @return
     *          verdadeiro se o metadado existir.
     */
    public boolean hasMeta(M meta) {
        return metas.contains(meta);
    }


    /**
     * Verifica se o cabeçalho do documento NCL possui algum metadado.
     *
     * @return
     *          verdadeiro se o corpo do cabeçalho NCL possuir algum metadado.
     */
    public boolean hasMeta() {
        return !metas.isEmpty();
    }


    /**
     * Retorna os metadados do cabeçalho do documento NCL.
     *
     * @return
     *          objeto Iterable contendo os metadados do cabeçalho do documento NCL.
     */
    public Iterable<M> getMetas() {
        return metas;
    }


    /**
     * Adiciona um metadado ao cabeçalho do documento NCL.
     *
     * @param metadata
     *          Objeto representando a arvore RDF de metadados a ser adicionada.
     * @return
     *          Verdadeiro se o metadado foi adicionado.
     *
     * @see TreeSet#add
     */
    public boolean addMetadata(MT metadata) {
        if(metadatas.add(metadata)){
            //Se metadata existe, atribui este como seu parente
            if(metadata != null)
                metadata.setParent(this);

            return true;
        }
        return false;
    }


    /**
     * Remove um metadado do cabeçalho do documento NCL.
     *
     * @param metadata
     *          Objeto representando a arvore RDF de metadados a ser removida.
     * @return
     *          Verdadeiro se o metadado foi removido.
     *
     * @see TreeSet#remove
     */
    public boolean removeMetadata(MT metadata) {
        if(metadatas.remove(metadata)){
            //Se metadata existe, retira o seu parentesco
            if(metadata != null)
                metadata.setParent(null);

            return true;
        }
        return false;
    }


    /**
     * Verifica se o cabeçalho do documento NCL possui um metadado.
     *
     * @param metadata
     *          elemento representando o metadado a ser verificado.
     * @return
     *          verdadeiro se o metadado existir.
     */
    public boolean hasMetadata(MT metadata) {
        return metadatas.contains(metadata);
    }


    /**
     * Verifica se o cabeçalho do documento NCL possui algum metadado.
     *
     * @return
     *          verdadeiro se o corpo do cabeçalho NCL possuir algum metadado.
     */
    public boolean hasMetadata() {
        return !metadatas.isEmpty();
    }


    /**
     * Retorna os metadados do cabeçalho do documento NCL.
     *
     * @return
     *          objeto Iterable contendo os metadados do cabeçalho do documento NCL.
     */
    public Iterable<MT> getMetadatas() {
        return metadatas;
    }


    
    public String parse(int ident) {
        String space, content;

        if(ident < 0)
            ident = 0;
        
        // Element indentation
        space = "";
        for(int i = 0; i < ident; i++)
            space += "\t";
        
        
        // <context> element and attributes declaration
        content = space + "<context";
        if(getId() != null)
            content += " id='" + getId() + "'";
        if(getRefer() != null)
            content += " refer='" + getRefer().getId() + "'";
        
        // Test if the media has content
        if(hasMeta() || hasMetadata() || hasPort() || hasProperty() || hasNode() || hasLink()){
            content += ">\n";

            if(hasMeta()){
                for(M meta : metas)
                    content += meta.parse(ident + 1);
            }
            if(hasMetadata()){
                for(MT metadata : metadatas)
                    content += metadata.parse(ident + 1);
            }
            if(hasPort()){
                for(Pt port : ports)
                    content += port.parse(ident + 1);
            }
            if(hasProperty()){
                for(Pp property : properties)
                    content += property.parse(ident + 1);
            }
            if(hasNode()){
                for(N node : nodes)
                    content += node.parse(ident + 1);
            }
            if(hasLink()){
                for(L link : links)
                    content += link.parse(ident + 1);
            }
            
            // <context> element end declaration
            content += space + "</context>\n";
        }
        else
            content += "/>\n";
        
        return content;
    }

    /**
     * Compara o contexto atual a um outro contexto através do identificador.
     *
     * @param other
     *  Contexto ao qual se deseja compaar o atual.
     * @return
     *      Retorna 0, caso os contextos sejam iguais.
     *      Retorna um inteiro diferente de zero, caso sejam diferentes.
     */
    public int compareTo(N other) {
        return getId().compareTo(other.getId());
    }


    public boolean validate() {
        cleanWarnings();
        cleanErrors();

        boolean valid = true;

        if(getId() == null){
            addError("Elemento não possui atributo obrigatório id.");
            valid = false;
        }
        if(getRefer() != null && (getRefer().compareTo(this) == 0 || findContext(nodes) != null)){
            addError("Elemento não pode fazer referência a si mesmo.");
            valid = false;
        }

        if(hasMeta()){
            for(M meta : metas){
                valid &= meta.validate();
                addWarning(meta.getWarnings());
                addError(meta.getErrors());
            }
        }
        if(hasMetadata()){
            for(MT metadata : metadatas){
                valid &= metadata.validate();
                addWarning(metadata.getWarnings());
                addError(metadata.getErrors());
            }
        }
        if(hasPort()){
            for(Pt port : ports){
                valid &= port.validate();
                addWarning(port.getWarnings());
                addError(port.getErrors());
            }
        }
        if(hasProperty()){
            for(Pp property : properties){
                valid &= property.validate();
                addWarning(property.getWarnings());
                addError(property.getErrors());
            }
        }
        if(hasNode()){
            for(N node : nodes){
                valid &= node.validate();
                addWarning(node.getWarnings());
                addError(node.getErrors());
            }
        }
        if(hasLink()){
            for(L link : links){
                valid &= link.validate();
                addWarning(link.getWarnings());
                addError(link.getErrors());
            }
        }

        return valid;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try{
            if(localName.equals("context") && !insideContext){
                cleanWarnings();
                cleanErrors();
                insideContext = true;
                for(int i = 0; i < attributes.getLength(); i++){
                    if(attributes.getLocalName(i).equals("id"))
                        setId(attributes.getValue(i));
                    else if(attributes.getLocalName(i).equals("refer"))
                        setRefer((C) new NCLContext(attributes.getValue(i)));//cast retirado na correcao das referencias
                }
            }
            else if(localName.equals("meta")){
                M child = createMeta();
                child.startElement(uri, localName, qName, attributes);
                addMeta(child);
            }
            else if(localName.equals("metadata")){
                MT child = createMetadata();
                child.startElement(uri, localName, qName, attributes);
                addMetadata(child);
            }
            else if(localName.equals("port")){
                Pt child = createPort();
                child.startElement(uri, localName, qName, attributes);
                addPort(child);
            }
            else if(localName.equals("property")){
                Pp child = createProperty();
                child.startElement(uri, localName, qName, attributes);
                addProperty(child);
            }
            else if(localName.equals("media")){
                N child = createMedia();
                child.startElement(uri, localName, qName, attributes);
                addNode(child);
            }
            else if(localName.equals("context") && insideContext){
                N child = createContext();
                child.startElement(uri, localName, qName, attributes);
                addNode(child);
            }
            else if(localName.equals("switch")){
                N child = createSwitch();
                child.startElement(uri, localName, qName, attributes);
                addNode(child);
            }
            else if(localName.equals("link")){
                L child = createLink();
                child.startElement(uri, localName, qName, attributes);
                addLink(child);
            }
        }
        catch(NCLInvalidIdentifierException ex){
            addError(ex.getMessage());
        }
    }


    @Override
    public void endDocument() {
        if(getParent() != null){
            if(getRefer() != null)
                contextReference();
        }

        if(hasMeta()){
            for(M meta : metas){
                meta.endDocument();
                addWarning(meta.getWarnings());
                addError(meta.getErrors());
            }
        }
        if(hasMetadata()){
            for(MT metadata : metadatas){
                metadata.endDocument();
                addWarning(metadata.getWarnings());
                addError(metadata.getErrors());
            }
        }
        if(hasPort()){
            for(Pt port : ports){
                port.endDocument();
                addWarning(port.getWarnings());
                addError(port.getErrors());
            }
        }
        if(hasProperty()){
            for(Pp property : properties){
                property.endDocument();
                addWarning(property.getWarnings());
                addError(property.getErrors());
            }
        }
        if(hasNode()){
            for(N node : nodes){
                node.endDocument();
                addWarning(node.getWarnings());
                addError(node.getErrors());
            }
        }
        if(hasLink()){
            for(L link : links){
                link.endDocument();
                addWarning(link.getWarnings());
                addError(link.getErrors());
            }
        }
    }

    /**
     * Verifica se um contexto referenciado (cujo id é atribuido ao elemento refer)
     * existe no corpo do documento NCL. Caso não exista, uma advertência é adicionada
     * a lista de advertências.
     */

    private void contextReference() {
        //Search for the interface inside the node
        NCLElement body = getParent();

        while(!(body instanceof NCLBody)){
            body = body.getParent();
            if(body == null){
                addWarning("Could not find a body");
                return;
            }
        }

        setRefer(findContext(((NCLBody) body).getNodes()));
    }

    /**
     * Dado um conjunto de nós, procura pelo contexto referenciado (cujo id é
     * atribuido ao elemento refer).
     * @param nodes
     *  Conjunto de nós que se deseja pesquisar
     * @return
     *  O contexto referenciado pelo atual, caso ele exista. Caso não encontre,
     * adiciona uma advertencia a lista d asvertencias e retorna null.
     */
    private C findContext(Iterable<N> nodes) {
        for(N n : nodes){
            if(n instanceof NCLContext){
                if(n.getId().equals(getRefer().getId()))
                    return (C) n;
                else if( ((NCLContext) n).hasNode()){
                    Iterable<N> cnodes = ((NCLContext) n).getNodes();
                    C c = findContext(cnodes);
                    if(c != null)
                        return (C) c;
                }
            }
            else if(n instanceof NCLSwitch){
                if( ((NCLSwitch) n).hasNode()){
                    Iterable<N> snodes = ((NCLSwitch) n).getNodes();
                    C c = findContext(snodes);
                    if(c != null)
                        return (C) c;
                }
            }
        }

        addWarning("Could not find media with id: " + getRefer().getId());
        return null;
    }


    /**
     * Função de criação do elemento filho <i>meta</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>meta</i>.
     */
    protected M createMeta() {
        return (M) new NCLMeta(getReader(), this);
    }


    /**
     * Função de criação do elemento filho <i>metadata</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>metadata</i>.
     */
    protected MT createMetadata() {
        return (MT) new NCLMetadata(getReader(), this);
    }


    /**
     * Função de criação do elemento filho <i>port</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>port</i>.
     */
    protected Pt createPort() {
        return (Pt) new NCLPort(getReader(), this);
    }


    /**
     * Função de criação do elemento filho <i>property</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>property</i>.
     */
    protected Pp createProperty() {
        return (Pp) new NCLProperty(getReader(), this);
    }


    /**
     * Função de criação do elemento filho <i>media</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>media</i>.
     */
    protected N createMedia() {
        return (N) new NCLMedia(getReader(), this);
    }


    /**
     * Função de criação do elemento filho <i>context</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>context</i>.
     */
    protected N createContext() {
        return (N) new NCLContext(getReader(), this);
    }


    /**
     * Função de criação do elemento filho <i>switch</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>switch</i>.
     */
    protected N createSwitch() {
        return (N) new NCLSwitch(getReader(), this);
    }


    /**
     * Função de criação do elemento filho <i>link</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>link</i>.
     */
    protected L createLink() {
        return (L) new NCLLink(getReader(), this);
    }
}
