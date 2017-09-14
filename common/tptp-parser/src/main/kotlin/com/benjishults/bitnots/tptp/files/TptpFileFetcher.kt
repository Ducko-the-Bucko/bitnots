package com.benjishults.bitnots.tptp.files

import java.nio.file.Path
import java.nio.file.FileSystems
import com.benjishults.bitnots.tptp.TptpProperties

enum class TptpDomain(val field: String, val subfield: String) {
    COL("Logic", "Combinatory Logic"),
    LCL("Logic", "Logic Calculi"),
    HEN("Logic", "Henkin Models"),
    SET("Mathematics", "Set Theory"),
    SEU("Mathematics", "Set Theory Continued 1"),
    SEV("Mathematics", "Set Theory Continued 2"),
    GRA("Mathematics", "Graph Theory"),
    REL("Mathematics", "Relation Algebra"),
    BOO("Mathematics", "Boolean Algebra"),
    ROB("Mathematics", "Robbins Algebra"),
    LDA("Mathematics", "Left Distributive"),
    LAT("Mathematics", "Lattices"),
    QUA("Mathematics", "Quantales"),
    KLE("Mathematics", "Kleene Algebra"),
    GRP("Mathematics", "Groups"),
    RNG("Mathematics", "Rings"),
    FLD("Mathematics", "Fields"),
    LIN("Mathematics", "Linear Algebra"),
    HAL("Mathematics", "Homological Alg"),
    RAL("Mathematics", "Real Algebra"),
    ALG("Mathematics", "General Algebra"),
    NUM("Mathematics", "Number Theory"),
    NUN("Mathematics", "Number Theory Continued 1"),
    TOP("Mathematics", "Topology"),
    ANA("Mathematics", "Analysis"),
    GEO("Mathematics", "Geometry"),
    CAT("Mathematics", "Category Theory"),
    COM("Computer Science", "Computing Theory"),
    KRS("Computer Science", "Knowledge Representation"),
    NLP("Computer Science", "Natural Language Processing"),
    PLA("Computer Science", "Planning"),
    AGT("Computer Science", "Agents"),
    SWB("Computer Science", "Semantic Web"),
    CSR("Computer Science", "Commonsense Reasoning"),
    DAT("Computer Science", "Data Structures"),
    SWC("Computer Science", "Software Creation"),
    SWV("Computer Science", "Software Verification"),
    SWW("Computer Science", "Software Verification Continued 1"),
    BIO("Science and Engineering", "Biology"),
    HWC("Science and Engineering", "Hardware Creation"),
    HWV("Science and Engineering", "Hardware Verification"),
    MED("Science and Engineering", "Medicine"),
    PRO("Science and Engineering", "Processes"),
    PRD("Science and Engineering", "Products"),
    SCT("Social Sciences", "Social Choice Theory"),
    MGT("Social Sciences", "Management"),
    GEG("Social Sciences", "Geography"),
    PHI("Arts and Humanities", "Philosophy"),
    ARI("Other", "Arithmetic"),
    SYN("Other", "Syntactic"),
    SYO("Other", "Syntactic Continued 1"),
    PUZ("Other", "Puzzles"),
    MSC("Other", "Miscellaneous");

    fun findBySubfield(subfield: String) = values().find { it.subfield == subfield }

}

enum class TptpFormulaForm(val form: Char) {
    CNF('-'),
    FOF('+'),
    TFF('_'),
    TFF_WITH_ARITHMETIC('='),
    THF('^');

    fun findByForm(form: Char) = values().find { it.form == form }

}

class TptpFileFetcher {

    // A regular expression for recognizing problem file names is "[A-Z]{3}[0-9]{3}[-+^=_][1-9][0-9]*(\.[0-9]{3})*\.p".
    // A regular expression for recognizing axiom file names is "[A-Z]{3}[0-9]{3}[-+^=_][1-9][0-9]*\.ax".

    companion object {
        val FILE_SYSTEM = FileSystems.getDefault()
        fun formatAxiomatizationNumber(version: Int): String =
                String.format("%03d", version)
    }

    fun findAxiomsFile(domain: TptpDomain, form: TptpFormulaForm, axiomatizationNumber: Int = 1, version: Int = 0): Path =
            if (domain === TptpDomain.SET && axiomatizationNumber == 7) {
                FILE_SYSTEM.getPath(TptpProperties.getBaseFolderName() as String,
                        "Axioms",
                        domain.toString() + formatAxiomatizationNumber(axiomatizationNumber),
                        "${domain.toString()}${formatAxiomatizationNumber(axiomatizationNumber)}${form.form}$version.ax")
            } else
                FILE_SYSTEM.getPath(TptpProperties.getBaseFolderName() as String,
                        "Axioms",
                        "${domain.toString()}${formatAxiomatizationNumber(axiomatizationNumber)}${form.form}$version.ax")
}
