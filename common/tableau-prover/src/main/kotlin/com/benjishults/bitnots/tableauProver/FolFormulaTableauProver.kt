package com.benjishults.bitnots.tableauProver

import com.benjishults.bitnots.tableau.FolTableau
import com.benjishults.bitnots.tableau.strategy.FolStepStrategy
import com.benjishults.bitnots.tableau.strategy.FolUnificationClosingStrategy

class FolFormulaTableauProver(
        override val finishingStrategy: FolUnificationClosingStrategy,
        override val stepStrategy: FolStepStrategy
) : TableauProver<FolTableau>
