package com.benjishults.bitnots.tptp.parser

import com.benjishults.bitnots.tptp.files.TptpDomain
import com.benjishults.bitnots.tptp.files.TptpFormulaForm
import com.benjishults.bitnots.tptp.files.TptpProblemFileDescriptor
import org.junit.Assert.assertTrue
import org.junit.Test
import java.nio.file.Path

class TptpProblemFileDescriptorTest {

    @Test
    fun testTptpProblemFileDescriptor() {
        assertTrue(
                TptpProblemFileDescriptor(TptpDomain.AGT, TptpFormulaForm.FOF, 0, 1) ==
                        TptpProblemFileDescriptor.parseTptpPath(Path.of("AGT000+1.p")))
        assertTrue(
                TptpProblemFileDescriptor(TptpDomain.SYN, TptpFormulaForm.FOF, 10, 2) ==
                        TptpProblemFileDescriptor.parseTptpPath(Path.of("SYN010+2.p")))
        assertTrue(
                TptpProblemFileDescriptor(TptpDomain.AGT, TptpFormulaForm.FOF, 0, 1, 0) ==
                        TptpProblemFileDescriptor.parseTptpPath(Path.of("AGT000+1.000.p")))
    }

}
