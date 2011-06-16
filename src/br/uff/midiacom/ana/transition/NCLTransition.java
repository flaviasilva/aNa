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
package br.uff.midiacom.ana.transition;

import br.uff.midiacom.ana.interfaces.NCLTime;
import br.uff.midiacom.ana.NCLElement;
import br.uff.midiacom.ana.NCLIdentifiableElement;
import br.uff.midiacom.ana.NCLInvalidIdentifierException;
import br.uff.midiacom.ana.NCLValues.NCLColor;
import br.uff.midiacom.ana.NCLValues.NCLTransitionDirection;
import br.uff.midiacom.ana.NCLValues.NCLTransitionSubtype;
import br.uff.midiacom.ana.NCLValues.NCLTransitionType;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;


/**
 * Esta classe define uma transição da <i>Nested Context Language</i> (NCL).
 * As transições especificam efeitos de transição que os descritores podem utilizar
 * na exibição de objetos de mídia em uma aplicação NCL.As transições são definidas
 * no cabeçalho do documento, em uma base de transições definida pelo elemento
 * <i>transitionBase</i>.
 * As transições são associadas as mídias através dos atributos de descritor
 * <i>transIn</i> (especifica uma transição de entrada) e <i>transOut</i> (especifica
 * uma transição de saída)
 *
 * <br/>
 *
 * @see br.uff.midiacom.ana.transition.NCLTransitionBase
 *
 * @see <a href="http://www.dtv.org.br/download/pt-br/ABNTNBR15606-2_2007Vc3_2008.pdf">
 *          ABNT NBR 15606-2:2007</a>
 */
public class NCLTransition<T extends NCLTransition> extends NCLIdentifiableElement implements Comparable<T> {

    private NCLTransitionType type;
    private NCLTransitionSubtype subtype;
    private NCLTime dur;
    private Double startProgress;
    private Double endProgress;
    private NCLTransitionDirection direction;
    private NCLColor fadeColor;
    private Integer horRepeat;
    private Integer vertRepeat;
    private Integer borderWidth;
    private NCLColor borderColor;


    /**
     * Construtor do elemento <i>transition</i> da <i>Nested Context Language</i> (NCL).
     *
     * @param id
     *          identificador da transição.
     * @throws br.pensario.NCLInvalidIdentifierException
     *          se o identificador da transição não for válido.
     */
    public NCLTransition(String id) throws NCLInvalidIdentifierException {
        setId(id);
    }


    /**
     * Construtor do elemento <i>transition</i> da <i>Nested Context Language</i> (NCL).
     *
     * @param reader
     *          elemento representando o leitor XML do parser SAX.
     * @param parent
     *          elemento NCL representando o elemento pai.
     */
    public NCLTransition(XMLReader reader, NCLElement parent) {
        setReader(reader);
        setParent(parent);

        getReader().setContentHandler(this);
    }


    /**
     * Define o tipo da transição.
     *
     * @see br.uff.midiacom.ana.NCLValues.NCLTransitionType
     *
     * @param type
     *          elemento representando o tipo da transição.
     */
    public void setType(NCLTransitionType type) {
        this.type = type;
    }


    /**
     * Retorna o tipo da transição.
     *
     * @see br.uff.midiacom.ana.NCLValues.NCLTransitionType
     *
     * @return
     *          elemento representando o tipo da transição.
     */
    public NCLTransitionType getType() {
        return type;
    }


    /**
     * Atribui um subtipo a transição.
     *
     * @see br.uff.midiacom.ana.NCLValues.NCLTransitionSubtype
     *
     * @param subtype
     *          elemento representando o subtipo da transição.
     */
    public void setSubtype(NCLTransitionSubtype subtype) {
        this.subtype = subtype;
    }


    /**
     * Retorna o subtipo da transição.
     *
     * @see br.uff.midiacom.ana.NCLValues.NCLTransitionSubtype
     *
     * @return
     *          elemento representando o subtipo da transição.
     */
    public NCLTransitionSubtype getSubtype() {
        return subtype;
    }


    /**
     * Atribui uma duração a transição.
     *
     * @param dur
     *          elemento representando a duração da transição.
     */
    public void setDur(NCLTime dur) {
        this.dur = dur;
    }


    /**
     * Retorna a duração da transição.
     *
     * @return
     *          elemento representando a duração da transição.
     */
    public NCLTime getDur() {
        return dur;
    }


    /**
     * Especifica o quanto de progresso para a transição deve ser assumido ao iniciar
     * e execução. O valor deste atributo deve estar contido no intervalo [0.0,1.0].
     *
     * @param startProgress
     *          real representando a porcentagem da mídia que estará visível no
     * inicio da transição.
    */
     
    public void setStartProgress(Double startProgress) {
        this.startProgress = startProgress;
    }


    /**
     * Retorna a condição inicial do processo de transição da mídia.
     *
     * @return
     *          real representando a porcentagem da mídia que estará visível no
     * inicio da transição.
     */
    public Double getStartProgress() {
        return startProgress;
    }


    /**
     * Especifica até quanto de progreso para a transição deve ser realizado até
     * terminar a execução. Ao atingir este valor (pertencente ao intervalo
     * [0.0,1.0]), toda a mídia é revelada, encerrando o processo de transição.
     *
     * @param endProgress
     *          real representando a porcentagem da mídia que deverá ser exibida
     * para que o processo de transição termine.
     */
    public void setEndProgress(Double endProgress) {
        this.endProgress = endProgress;
    }


    /**
     * Retorna a porcentagem de exibição da mídia que representa a condição de
     * parada da transição.
     *
     * @return
     *          real representando a porcentagem da mídia que deverá ser exibida
     * para que o processo de transição termine.
     */
    public Double getEndProgress() {
        return endProgress;
    }


    /**
     * Especifica a direção em que ocorrerá a transição.
     *
     * @see br.uff.midiacom.ana.NCLValues.NCLTransitionDirection
     *
     * @param direction
     *          elemento representando a direção.
     */
    public void setDirection(NCLTransitionDirection direction) {
        this.direction = direction;
    }


    /**
     * Retorna a direção em que ocorrerá a transição.
     *
     * @see br.uff.midiacom.ana.NCLValues.NCLTransitionDirection
     *
     * @return
     *          elemento representando a direção.
     */
    public NCLTransitionDirection getDirection() {
        return direction;
    }


    /**
     * Especifica a cor utilizada para efeitos de transição do tipo fade.
     *
     * @see br.uff.midiacom.ana.NCLValues.NCLColor
     *
     * @param fadeColor
     *          cor associada a transição de fade.
     */
    public void setFadeColor(NCLColor fadeColor) {
        this.fadeColor = fadeColor;
    }


    /**
     * Retorna a cor utilizada para efeitos de transição do tipo fade.
     *
     * @return
     *          cor associada a transição de fade.
     */
    public NCLColor getFadeColor() {
        return fadeColor;
    }


    /**
     * Determina o numero de repetições da transição no eixo horizontal.
     *
     * @param horRepeat
     *          inteiro representando o número de repetições.
     */
    public void setHorRepeat(Integer horRepeat) {
        this.horRepeat = horRepeat;
    }


    /**
     * Retorna o numero de repetições da transição no eixo horizontal.
     *
     * @return
     *          inteiro representando o número de repetições.
     */
    public Integer getHorRepeat() {
        return horRepeat;
    }


    /**
     * Determina o numero de repetições da transição no eixo vertical.
     *
     * @param vertRepeat
     *          inteiro representando o número de repetições.
     */
    public void setVertRepeat(Integer vertRepeat) {
        this.vertRepeat = vertRepeat;
    }


    /**
     * Retorna o numero de repetições da transição no eixo vertical.
     *
     * @return
     *          inteiro representando o número de repetições.
     */
    public Integer getVertRepeat() {
        return vertRepeat;
    }


    /**
     * Determina a largura da moldura gerada ao longo da borda da transição.
     *
     * @param borderWidth
     *          inteiro representando a largura da borda.
     */
    public void setBorderWidth(Integer borderWidth) {
        this.borderWidth = borderWidth;
    }


    /**
     * Retorna a largura da moldura gerada ao longo da borda de transição.
     *
     * @return
     *          inteiro representando a largura da borda.
     */
    public Integer getBorderWidth() {
        return borderWidth;
    }


   
     /**
     * Determina a cor da moldura gerada ao longo da borda de transição.
     *
     * @see NCLColor
     *
     * @param borderColor
     *          cor da moldura
     */
    public void setBorderColor(NCLColor borderColor) {
        this.borderColor = borderColor;
    }


    /**
     * Retorna a cor da moldura gerada ao longo da borda de transição.
     *
     * @return
     *          cor da moldura.
     */
    public NCLColor getBorderColor() {
        return borderColor;
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
        content = space + "<transition";
        if(getId() != null)
            content += " id='" + getId() + "'";
        if(getType() != null)
            content += " type='" + getType().toString() + "'";
        if(getSubtype() != null)
            content += " subtype='" + getSubtype().toString() + "'";
        if(getDur() != null)
            content += " dur='" + getDur() + "'";
        if(getStartProgress() != null)
            content += " startProgress='" + getStartProgress() + "'";
        if(getEndProgress() != null)
            content += " endProgress='" + getEndProgress() + "'";
        if(getDirection() != null)
            content += " direction='" + getDirection().toString() + "'";
        if(getFadeColor() != null)
            content += " fadeColor='" + getFadeColor().toString() + "'";
        if(getHorRepeat() != null)
            content += " horRepeat='" + getHorRepeat() + "'";
        if(getVertRepeat() != null)
            content += " vertRepeat='" + getVertRepeat() + "'";
        if(getBorderWidth() != null)
            content += " borderWidth='" + getBorderWidth() + "'";
        if(getBorderColor() != null)
            content += " borderColor='" + getBorderColor().toString() + "'";
        content += "/>\n";

        return content;
    }

    /**
     * Compara o elemento transition atual com um outro qualquer, através do
     * identificador.
     *
     * @param other
     *      transição a qual se deseja comparar a atual.
     *
     * @return
     *      Retorna 0, caso as transições sejam iguais.
     *      Retorna um inteiro deiferente de zero, caso sejam diferentes.
     *
     */
    public int compareTo(T other) {
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
        if(getType() == null){
            addError("Elemento não possui atributo obrigatório type.");
            valid = false;
        }

        if(getSubtype() != null){
            if(getType() != null){
                if(!getSubtype().getType().equals(getType())){
                    addError("Tipo e subtipo da transição não estão corretos.");
                    valid = false;
                }
            }
            else{
                addError("Atributo subType não deve ser especificado sem o atributo type.");
                valid = false;
            }
        }

        if(getType() != null && getType().equals(NCLTransitionType.FADE) && getSubtype() != null && 
                (getSubtype().equals(NCLTransitionSubtype.FADE_FROM_COLOR) || getSubtype().equals(NCLTransitionSubtype.FADE_FROM_COLOR))){
            if(getFadeColor() == null){
                addWarning("Atributo fadeColor deve ser especificado.");
                valid = false;
            }
        }
        else{
            if(getFadeColor() != null){
                addWarning("Atributo fadeColor não deve ser especificado.");
                valid = false;
            }
        }

        return valid;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try{
            cleanWarnings();
            cleanErrors();
            for(int i = 0; i < attributes.getLength(); i++){
                if(attributes.getLocalName(i).equals("id"))
                    setId(attributes.getValue(i));
                else if(attributes.getLocalName(i).equals("type")){
                    for(NCLTransitionType t : NCLTransitionType.values()){
                        if(t.toString().equals(attributes.getValue(i)))
                            setType(t);
                    }
                }
                else if(attributes.getLocalName(i).equals("subtype")){
                    for(NCLTransitionSubtype s : NCLTransitionSubtype.values()){
                        if(s.toString().equals(attributes.getValue(i)))
                            setSubtype(s);
                    }
                }
                else if(attributes.getLocalName(i).equals("dur")){
                    setDur(new NCLTime(attributes.getValue(i)));
                }
                else if(attributes.getLocalName(i).equals("startProgress")){
                    setStartProgress(new Double(attributes.getValue(i)));
                }
                else if(attributes.getLocalName(i).equals("endProgress")){
                    setEndProgress(new Double(attributes.getValue(i)));
                }
                else if(attributes.getLocalName(i).equals("direction")){
                    for(NCLTransitionDirection d : NCLTransitionDirection.values()){
                        if(d.toString().equals(attributes.getValue(i)))
                            setDirection(d);
                    }
                }
                else if(attributes.getLocalName(i).equals("fadeColor")){
                    for(NCLColor c : NCLColor.values()){
                        if(c.toString().equals(attributes.getValue(i)))
                            setFadeColor(c);
                    }
                }
                else if(attributes.getLocalName(i).equals("horRepeat")){
                    setHorRepeat(new Integer(attributes.getValue(i)));
                }
                else if(attributes.getLocalName(i).equals("vertRepeat")){
                    setVertRepeat(new Integer(attributes.getValue(i)));
                }
                else if(attributes.getLocalName(i).equals("borderWidth")){
                    setBorderWidth(new Integer(attributes.getValue(i)));
                }
                else if(attributes.getLocalName(i).equals("borderColor")){
                    for(NCLColor c : NCLColor.values()){
                        if(c.toString().equals(attributes.getValue(i)))
                            setBorderColor(c);
                    }
                }
            }
        }
        catch(NCLInvalidIdentifierException ex){
            addError(ex.getMessage());
        }
    }
}
