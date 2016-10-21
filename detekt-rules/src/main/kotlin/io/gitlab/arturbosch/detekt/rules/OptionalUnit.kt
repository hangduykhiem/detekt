package io.gitlab.arturbosch.detekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Location
import io.gitlab.arturbosch.detekt.api.Rule
import org.jetbrains.kotlin.psi.KtNamedFunction

/**
 * @author Artur Bosch
 */
class OptionalUnit(config: Config = Config.EMPTY) : Rule("OptionalUnit", Severity.Style, config) {

	override fun visitNamedFunction(function: KtNamedFunction) {
		if (function.funKeyword == null) return
		if (function.hasDeclaredReturnType()) {
			val typeReference = function.typeReference
			typeReference?.typeElement?.text?.let {
				if (it == "Unit") {
					addFindings(CodeSmell(id, Location.from(typeReference)))
				}
			}
		}
		super.visitNamedFunction(function)
	}
}