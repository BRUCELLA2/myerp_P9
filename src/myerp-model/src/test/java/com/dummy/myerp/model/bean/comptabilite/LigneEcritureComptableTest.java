package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class LigneEcritureComptableTest {

    @Test
    public void ligneEcritureComptable() {
        CompteComptable compteComptable = new CompteComptable();
        LigneEcritureComptable ligneEcritureComptable =
                new LigneEcritureComptable(compteComptable, "ligne ecriture comptable",
                                            BigDecimal.valueOf(10.05), BigDecimal.valueOf(15.05));

        Assert.assertTrue(ligneEcritureComptable.toString(),ligneEcritureComptable.getLibelle() == "ligne ecriture comptable" &&
                            ligneEcritureComptable.getCompteComptable().equals(compteComptable) &&
                            ligneEcritureComptable.getDebit().equals(BigDecimal.valueOf(10.05)) &&
                            ligneEcritureComptable.getCredit().equals(BigDecimal.valueOf(15.05)));
    }

}