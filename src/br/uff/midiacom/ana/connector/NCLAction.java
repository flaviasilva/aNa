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
package br.uff.midiacom.ana.connector;

import br.uff.midiacom.ana.Element;


/**
 * Esta interface define comportamentos comuns aos elementos do tipo ação de um
 * conector da <i>Nested Context Language</i> (NCL).
 * As classes que utilizam essa interface representam os elementos simpleAction 
 * e compoundAction.<br/>
 *
 *@see NCLSimpleAction
 *@see NCLCompoundAction
 *@see <a href="http://www.dtv.org.br/download/pt-br/ABNTNBR15606-2_2007Vc3_2008.pdf">
 *          ABNT NBR 15606-2:2007</a>
 */

public interface NCLAction<T, P extends NCLConnectorParam> extends Comparable<T>, Element {

    /**
 * Define o atributo delay, que representa o tempo decorrido entre a ativação do
 * elo e o disparo da ação, em segundos.
 * @param delay
 *          inteiro representando o delay em segundos.
 * @throws IllegalArgumentException
 *          dispara uma exceção caso o valor passado como parametro seja negativo.
 */
    public void setDelay(Integer delay);


 /**
 * Define o atributo delay, que representa o tempo decorrido entre a ativação do
 * elo e o disparo da ação, em segundos.
 * @param delay
 *          Objeto do tipo connectorParam representando o delay.
 */

    public void setDelay(P delay);
    
    
 /**
  * Retorna o valor do atributo delay, representando o atraso entre o disparo do
  * elo e a execução da ação.
  *
  * @return
  *          inteiro contendo o delay, em segundos.
 */
    public Integer getDelay();


 /**
  * Retorna o valor do atributo delay, representando o atraso entre o disparo do
  * elo e a execução da ação.
  *
  * @return
  *          Objeto do tipo connectorParam representando o delay
 */
    public P getParamDelay();
}
