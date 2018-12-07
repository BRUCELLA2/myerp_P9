package com.dummy.myerp.model.bean.comptabilite;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class LigneEcritureComptableTest {

    @Test
    public void validateSettersAndGetters() {
        PojoClass EcritureComptablePojo = PojoClassFactory.getPojoClass(LigneEcritureComptable.class);

        Validator validator = ValidatorBuilder.create()
        .with(new SetterTester(), new GetterTester())
        .build();
        validator.validate(EcritureComptablePojo);
    }

    @Test
    public void ligneEcritureComptable() {
        CompteComptable compteComptable = new CompteComptable();

        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(compteComptable,
                                                    "ligne ecriture comptable",
                                                            BigDecimal.valueOf(10.05),
                                                            BigDecimal.valueOf(15.05));

        Assert.assertEquals(ligneEcritureComptable.toString(), ligneEcritureComptable.getLibelle(), "ligne ecriture comptable");
        Assert.assertEquals(ligneEcritureComptable.toString(), ligneEcritureComptable.getCompteComptable(), compteComptable);
        Assert.assertEquals(ligneEcritureComptable.toString(), ligneEcritureComptable.getDebit(), BigDecimal.valueOf(10.05));
        Assert.assertEquals(ligneEcritureComptable.toString(), ligneEcritureComptable.getCredit(), BigDecimal.valueOf(15.05));

        Assert.assertNotEquals(ligneEcritureComptable.toString(), ligneEcritureComptable.getLibelle(), "Mauvaise ligne ecriture comptable");
        Assert.assertNotEquals(ligneEcritureComptable.toString(), ligneEcritureComptable.getCompteComptable(), null);
        Assert.assertNotEquals(ligneEcritureComptable.toString(), ligneEcritureComptable.getDebit(), BigDecimal.valueOf(11));
        Assert.assertNotEquals(ligneEcritureComptable.toString(), ligneEcritureComptable.getCredit(), BigDecimal.valueOf(20));
    }

}