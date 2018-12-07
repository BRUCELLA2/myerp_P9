package com.dummy.myerp.model.bean.comptabilite;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;



public class CompteComptableTest {


    @Test
    public void validateSettersAndGetters() {
        PojoClass EcritureComptablePojo = PojoClassFactory.getPojoClass(CompteComptable.class);

        Validator validator = ValidatorBuilder.create()
        .with(new SetterTester(), new GetterTester())
        .build();
        validator.validate(EcritureComptablePojo);
    }

    @Test
    public void getByNumero() {
        CompteComptable compteComptable = new CompteComptable(3, "compte test");
        CompteComptable compteComptable2 = new CompteComptable(2, "compte test 2");
        List<CompteComptable> listCompte = new ArrayList<>();
        listCompte.add(compteComptable);
        listCompte.add(compteComptable2);

        Assert.assertEquals(compteComptable.toString(), CompteComptable.getByNumero(listCompte, 3).getLibelle(), "compte test");
        Assert.assertNotEquals(compteComptable2.toString(), CompteComptable.getByNumero(listCompte, 2).getLibelle(),"compte test");
        Assert.assertNull("compte inexistant", CompteComptable.getByNumero(listCompte, 4));
    }

}