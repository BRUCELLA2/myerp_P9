package com.dummy.myerp.model.bean.comptabilite;


import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Test;

public class SequenceEcritureComptableTest {

    @Test
    public void validateSettersAndGetters() {
        PojoClass EcritureComptablePojo = PojoClassFactory.getPojoClass(SequenceEcritureComptable.class);

        Validator validator = ValidatorBuilder.create()
        .with(new SetterTester(), new GetterTester())
        .build();
        validator.validate(EcritureComptablePojo);
    }

    @Test
    public void sequenceEcritureComptable() {
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(2018, 50);

        Assert.assertEquals(sequenceEcritureComptable.toString(), sequenceEcritureComptable.getAnnee(), Integer.valueOf(2018));
        Assert.assertEquals(sequenceEcritureComptable.toString(), sequenceEcritureComptable.getDerniereValeur(), Integer.valueOf(50));
        Assert.assertNotEquals(sequenceEcritureComptable.toString(), sequenceEcritureComptable.getAnnee(), Integer.valueOf(1918));
        Assert.assertNotEquals(sequenceEcritureComptable.toString(), sequenceEcritureComptable.getDerniereValeur(), Integer.valueOf(10));
    }
}