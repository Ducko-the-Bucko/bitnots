package com.benjishults.bitnots.tptp.parser

import com.benjishults.bitnots.tptp.files.TptpDomain
import com.benjishults.bitnots.tptp.files.TptpFileFetcher
import com.benjishults.bitnots.tptp.files.TptpFormulaForm
import org.junit.Assert
import org.junit.Test
import java.nio.file.FileSystems

class ParserTest {

    private val path = FileSystems.getDefault().getPath("src/test/resources/PLA001-1.p")

    @Test
    fun cnfParserTest() {
        path.toFile().bufferedReader().use {
            try {
                TptpParser.parseFile(path).let {
                    Assert.assertEquals(16, it.inputs.size)
                    Assert.assertTrue(it.inputs.all { it is CnfAnnotatedFormula })
                    Assert.assertTrue((it.inputs.last() as CnfAnnotatedFormula).formulaRole === FormulaRoles.negated_conjecture)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    @Test
    fun fofParserTest1() {
        try {
            val path = TptpFileFetcher.findProblemFile(TptpDomain.PLA, TptpFormulaForm.FOF, 24, 1)
            TptpParser.parseFile(path).let {
                Assert.assertEquals(53, it.inputs.size)
                Assert.assertTrue(it.inputs.all { it is FofAnnotatedFormula })
                Assert.assertTrue((it.inputs.last() as FofAnnotatedFormula).formulaRole === FormulaRoles.conjecture)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    @Test
    fun fofParserTest2() {
        try {
            val path = TptpFileFetcher.findProblemFile(TptpDomain.PLA, TptpFormulaForm.FOF, 34, 1)
            TptpParser.parseFile(path).let {
                TptpParser.parseFile(path).let {
                    Assert.assertEquals(2, it.inputs.size)
                    Assert.assertTrue(it.inputs.all { it is FofAnnotatedFormula })
                    Assert.assertTrue("Was ${it.inputs.last()}", (it.inputs.last() as FofAnnotatedFormula).formulaRole === FormulaRoles.axiom)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

}