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

public class JournalComptableTest {

    @Test
    public void validateSettersAndGetters() {
        PojoClass EcritureComptablePojo = PojoClassFactory.getPojoClass(JournalComptable.class);

        Validator validator = ValidatorBuilder.create()
        .with(new SetterTester(), new GetterTester())
        .build();
        validator.validate(EcritureComptablePojo);
    }

    @Test
    public void getByCode() {

        JournalComptable journalComptable = new JournalComptable("FF", "journal test 1");
        JournalComptable journalComptable2 = new JournalComptable("GG", "journal test 2");
        List<JournalComptable> list = new ArrayList<>();
        list.add(journalComptable);
        list.add(journalComptable2);

        Assert.assertEquals(journalComptable.toString(), JournalComptable.getByCode(list, "FF").getLibelle(), "journal test 1");
        Assert.assertNotEquals(journalComptable2.toString(), JournalComptable.getByCode(list, "GG").getLibelle(), "journal test 1");
        Assert.assertNull("journal inexistant", JournalComptable.getByCode(list, "AA"));
    }
}