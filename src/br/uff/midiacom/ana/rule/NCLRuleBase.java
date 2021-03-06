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
package br.uff.midiacom.ana.rule;

import br.uff.midiacom.ana.NCLElement;
import br.uff.midiacom.ana.NCLIdentifiableElement;
import br.uff.midiacom.ana.NCLInvalidIdentifierException;
import br.uff.midiacom.ana.NCLValues.NCLImportType;
import br.uff.midiacom.ana.reuse.NCLImport;
import java.util.Set;
import java.util.TreeSet;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;


/**
 * Esta classe define uma base de regras de teste da <i>Nested Context Language</i> (NCL).
 * Uma base de regras, definida pelo elemento <i>ruleBase</i>, consiste numa
 * coleção de regras de teste, que serão utilizadas na avaliação de condições
 * destinadas a adaptação do conteúdo apresentado. <br/>
 *
 * @see <a href="http://www.dtv.org.br/download/pt-br/ABNTNBR15606-2_2007Vc3_2008.pdf">
 *          ABNT NBR 15606-2:2007</a>
 */
public class NCLRuleBase<T extends NCLTestRule, I extends NCLImport> extends NCLIdentifiableElement {

    private Set<T> rules = new TreeSet<T>();
    private Set<I> imports = new TreeSet<I>();


    /**
     * Construtor do elemento <i>ruleBase</i> da <i>Nested Context Language</i> (NCL).
     */
    public NCLRuleBase() {}


    /**
     * Construtor do elemento <i>ruleBase</i> da <i>Nested Context Language</i> (NCL).
     *
     * @param reader
     *          elemento representando o leitor XML do parser SAX.
     * @param parent
     *          elemento NCL representando o elemento pai.
     */
    public NCLRuleBase(XMLReader reader, NCLElement parent) {
        setReader(reader);
        setParent(parent);

        getReader().setContentHandler(this);
    }


    /**
     * Adiciona uma regra a base de regras.
     *
     * @param rule
     *          elemento representando a regra a ser adicionada.
     * @return
     *          verdadeiro se a regra foi adicionada.
     *
     * @see TreeSet#add
     */
    public boolean addRule(T rule) {
        if(rules.add(rule)){
            //Se rule existe, atribui este como seu parente
            if(rule != null)
                rule.setParent(this);

            return true;
        }
        return false;
    }


    /**
     * Remove uma regra da base de regras.
     *
     * @param rule
     *          elemento representando a regra a ser removida.
     * @return
     *          verdadeiro se a regra foi removida.
     *
     * @see TreeSet#remove
     */
    public boolean removeRule(T rule) {
        if(rules.remove(rule)){
            //Se rule existe, retira o seu parentesco
            if(rule != null)
                rule.setParent(null);

            return true;
        }
        return false;
    }


    /**
     * Verifica se a base de regras possui uma regra. Busca a partir de uma
     * referência a regra que se deseja verificar.
     *
     * @param rule
     *          elemento representando a regra a ser verificada.
     * @return
     *          verdadeiro se a regra existir.
     */
    public boolean hasRule(T rule) {
        return rules.contains(rule);
    }


    /**
     * Verifica se a base de regras possui alguma regra.
     *
     * @return
     *          verdadeiro se a base de regras possui alguma regra.
     */
    public boolean hasRule() {
        return !rules.isEmpty();
    }


    /**
     * Retorna as regras da base de regras.
     *
     * @return
     *          objeto Iterable contendo as regras da base de regras.
     */
    public Iterable<T> getRules() {
        return rules;
    }


     /**
     * Adiciona um importador de base à base de regras.
     *
     * @param importBase
     *          elemento representando o importador a ser adicionado.
     *
     * @see TreeSet#add
     */
    public boolean addImportBase(I importBase) {
        if(imports.add(importBase)){
            //Se importBase existe, atribui este como seu parente
            if(importBase != null)
                importBase.setParent(this);

            return true;
        }
        return false;
    }


    /**
     * Remove um importador de base da base de regras.
     *
     * @param importBase
     *          elemento representando o importador a ser removido.
     *
     * @see TreeSet#remove
     */
    public boolean removeImportBase(I importBase) {
        if(imports.remove(importBase)){
            //Se importBase existe, retira o seu parentesco
            if(importBase != null)
                importBase.setParent(null);

            return true;
        }
        return false;
    }


    /**
     * Verifica se a base de regras contém um importador de base. Busca a partir de um referência
     * ao importador que se deseja verificar.
     *
     * @param importBase
     *          elemento representando o importador a ser verificado.
     */
    public boolean hasImportBase(I importBase) {
        return imports.contains(importBase);
    }

    /**
     * Verifica se a base de regras possui algum importador de base.
     *
     * @return
     *          verdadeiro se a base de regras possuir algum importador de base.
     */
    public boolean hasImportBase() {
        return !imports.isEmpty();
    }


    /**
     * Retorna os importadores de base da base de regras.
     *
     * @return
     *          objeto Iterable contendo os importadores de base da base de regras.
     */
    public Iterable<I> getImportBases() {
        return imports;
    }


    public String parse(int ident) {
        String space, content;

        if(ident < 0)
            ident = 0;

        // Element indentation
        space = "";
        for(int i = 0; i < ident; i++)
            space += "\t";

        content = space + "<ruleBase";
        if(getId() != null)
            content += " id='" + getId() + "'";

        if(hasImportBase() || hasRule()){
            content += ">\n";

            if(hasImportBase()){
                for(I imp : imports)
                    content += imp.parse(ident + 1);
            }

            if(hasRule()){
                for(T rule : rules)
                    content += rule.parse(ident + 1);
            }

            content += space + "</ruleBase>\n";
        }
        else
            content += "/>\n";

        return content;
    }


    public boolean validate() {
        cleanWarnings();
        cleanErrors();

        boolean valid = true;

        if(!hasImportBase() && !hasRule()){
            addWarning("Elemento não possui elementos filhos em cardinalidade correta. Deve possuir ao menos uma regra ou importBase.");
            valid = false;
        }

        if(hasImportBase()){
            for(I imp : imports){
                valid &= imp.validate();
                addWarning(imp.getWarnings());
                addError(imp.getErrors());
            }
        }
        if(hasRule()){
            for(T rule : rules){
                valid &= rule.validate();
                addWarning(rule.getWarnings());
                addError(rule.getErrors());
            }
        }

        return valid;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try{
            if(localName.equals("ruleBase")){
                cleanWarnings();
                cleanErrors();
                for(int i = 0; i < attributes.getLength(); i++){
                    if(attributes.getLocalName(i).equals("id"))
                        setId(attributes.getValue(i));
                }
            }
            else if(localName.equals("importBase")){
                I child = createImportBase();
                child.startElement(uri, localName, qName, attributes);
                addImportBase(child);
            }
            else if(localName.equals("rule")){
                T child = createRule();
                child.startElement(uri, localName, qName, attributes);
                addRule(child);
            }
            else if(localName.equals("compositeRule")){
                T child = createCompositeRule();
                child.startElement(uri, localName, qName, attributes);
                addRule(child);
            }
        }
        catch(NCLInvalidIdentifierException ex){
            addError(ex.getMessage());
        }
    }


    @Override
    public void endDocument() {
        if(hasImportBase()){
            for(I imp : imports){
                imp.endDocument();
                addWarning(imp.getWarnings());
                addError(imp.getErrors());
            }
        }
        if(hasRule()){
            for(T rule : rules){
                rule.endDocument();
                addWarning(rule.getWarnings());
                addError(rule.getErrors());
            }
        }
    }


    /**
     * Função de criação do elemento filho <i>importBase</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>importBase</i>.
     */
    protected I createImportBase() {
        return (I) new NCLImport(NCLImportType.BASE, getReader(), this);
    }


    /**
     * Função de criação do elemento filho <i>rule</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>rule</i>.
     */
    protected T createRule() {
        return (T) new NCLRule(getReader(), this);
    }


    /**
     * Função de criação do elemento filho <i>compositeRule</i>.
     * Esta função deve ser sobrescrita em classes que estendem esta classe.
     *
     * @return
     *          elemento representando o elemento filho <i>compositeRule</i>.
     */
    protected T createCompositeRule() {
        return (T) new NCLCompositeRule(getReader(), this);
    }
}
