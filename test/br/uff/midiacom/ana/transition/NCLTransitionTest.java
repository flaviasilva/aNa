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

import br.uff.midiacom.ana.NCLInvalidIdentifierException;
import br.uff.midiacom.ana.NCLValues.NCLColor;
import br.uff.midiacom.ana.NCLValues.NCLTransitionDirection;
import br.uff.midiacom.ana.NCLValues.NCLTransitionSubtype;
import br.uff.midiacom.ana.NCLValues.NCLTransitionType;
import br.uff.midiacom.ana.interfaces.NCLTime;
import java.io.IOException;
import java.io.StringReader;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import static org.junit.Assert.*;


public class NCLTransitionTest {

    @Test
    public void test1() throws NCLInvalidIdentifierException {
        NCLTransition trans = new NCLTransition("tr1");
        trans.setType(NCLTransitionType.FADE);
        trans.setSubtype(NCLTransitionSubtype.CROSSFADE);
        trans.setDirection(NCLTransitionDirection.FORWARD);
        trans.setDur(new NCLTime(5));
        trans.setStartProgress(0.1);
        trans.setEndProgress(0.9);
        trans.setFadeColor(NCLColor.BLACK);
        trans.setHorRepeat(4);
        trans.setVertRepeat(6);
        trans.setBorderWidth(20);
        trans.setBorderColor(NCLColor.BLUE);

        String expResult = "<transition id='tr1' type='fade' subtype='crossfade' dur='5s' startProgress='0.1' endProgress='0.9'"+
                " direction='forward' fadeColor='black' horRepeat='4' vertRepeat='6' borderWidth='20' borderColor='blue'/>\n";
        String result = trans.parse(0);
        assertEquals(expResult, result);
    }

    @Test
    public void test2() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLTransition instance = new NCLTransition(reader, null);
            String expResult = "<transition id='tr1' type='fade' subtype='crossfade' dur='5s' startProgress='0.1' endProgress='0.9'"+
                " direction='forward' fadeColor='black' horRepeat='4' vertRepeat='6' borderWidth='20' borderColor='blue'/>\n";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(expResult)));

            String result = instance.parse(0);
            assertEquals(expResult, result);
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test_validacao1() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLTransition instance = new NCLTransition(reader, null);
            String xml = "<transition type='fade' subtype='crossfade' dur='5s' startProgress='0.1' endProgress='0.9'"+
                " direction='forward' fadeColor='black' horRepeat='4' vertRepeat='6' borderWidth='20' borderColor='blue'/>\n";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));
            
            assertFalse(instance.validate());
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test_validacao2() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLTransition instance = new NCLTransition(reader, null);
            String xml = "<transition id='tr1' dur='5s' startProgress='0.1' endProgress='0.9'"+
                " direction='forward' fadeColor='black' horRepeat='4' vertRepeat='6' borderWidth='20' borderColor='blue'/>\n";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            assertFalse(instance.validate());
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test_validacao3() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLTransition instance = new NCLTransition(reader, null);
            String xml = "<transition id='tr1' subtype='crossfade' dur='5s' startProgress='0.1' endProgress='0.9'"+
                " direction='forward' fadeColor='black' horRepeat='4' vertRepeat='6' borderWidth='20' borderColor='blue'/>\n";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            assertFalse(instance.validate());
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test_validacao4() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLTransition instance = new NCLTransition(reader, null);
            String xml = "<transition id='tr1' type='barWipe' subtype='crossfade' dur='5s' startProgress='0.1' endProgress='0.9'"+
                " direction='forward' fadeColor='black' horRepeat='4' vertRepeat='6' borderWidth='20' borderColor='blue'/>\n";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            assertFalse(instance.validate());
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test_validacao5() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLTransition instance = new NCLTransition(reader, null);
            String xml = "<transition id='tr1' type='barWipe' subtype='leftToRight' dur='5s' startProgress='0.1' endProgress='0.9'"+
                " direction='forward' fadeColor='black' horRepeat='4' vertRepeat='6' borderWidth='20' borderColor='blue'/>\n";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            assertFalse(instance.validate());
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test_validacao6() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLTransition instance = new NCLTransition(reader, null);
            String xml = "<transition id='tr1' type='barWipe' subtype='leftToRight' dur='5s' startProgress='0.1' endProgress='0.9'"+
                " direction='forward' horRepeat='4' vertRepeat='6' borderWidth='20' borderColor='blue'/>\n";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            boolean result = instance.validate();

            for(String msg : instance.getWarnings())
                System.out.println(msg);
            for(String msg : instance.getErrors())
                System.out.println(msg);

            assertTrue(result);
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }
}