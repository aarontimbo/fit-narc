package com.atimbo.recipe.transfer

import javax.validation.constraints.NotNull

/**
 * General ID class that all other transfer object classes extend
 */
class ATimboId {

    @NotNull
    String uuId
}
