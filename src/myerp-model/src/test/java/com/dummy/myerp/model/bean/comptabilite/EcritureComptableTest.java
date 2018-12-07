package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;


public class EcritureComptableTest {

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }

    @Test
    public void validateSettersAndGetters() {
        final PojoClass EcritureComptablePojo = PojoClassFactory.getPojoClass(EcritureComptable.class);

        final Validator validator = ValidatorBuilder.create()
                                .with(new SetterTester(), new GetterTester())
                                .build();
        validator.validate(EcritureComptablePojo);
    }

    @Test
    public void isEquilibree() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));

        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));

        Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
    }

    @Test
    public void getTotalDebit() {
        EcritureComptable ecriture = new EcritureComptable();

        ecriture.setLibelle("Débit 10.55");
        ecriture.getListLigneEcriture().add(this.createLigne(1, "6.50", null));
        ecriture.getListLigneEcriture().add(this.createLigne(1, "4.05", null));

        Assert.assertEquals(ecriture.toString(), ecriture.getTotalDebit(), BigDecimal.valueOf(10.55));
        Assert.assertNotEquals(ecriture.toString(), ecriture.getTotalDebit(), BigDecimal.valueOf(11));

        ecriture.getListLigneEcriture().clear();
        ecriture.setLibelle("Ecriture avec debit null - Débit 12.65");
        ecriture.getListLigneEcriture().add(this.createLigne(1, null, null));
        ecriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        ecriture.getListLigneEcriture().add(this.createLigne(1, "2.65", null));

        Assert.assertEquals(ecriture.toString(), ecriture.getTotalDebit(), BigDecimal.valueOf(12.65));
        Assert.assertNotEquals(ecriture.toString(), ecriture.getTotalDebit(), BigDecimal.valueOf(13));
    }

    @Test
    public void getTotalCredit() {
        EcritureComptable ecriture;
        ecriture = new EcritureComptable();

        ecriture.setLibelle("Crédit 20.75");
        ecriture.getListLigneEcriture().add(this.createLigne(1, null, "10"));
        ecriture.getListLigneEcriture().add(this.createLigne(1, null, "10.75"));

        Assert.assertEquals(ecriture.toString(), ecriture.getTotalCredit(), BigDecimal.valueOf(20.75));
        Assert.assertNotEquals(ecriture.toString(), ecriture.getTotalCredit(), BigDecimal.valueOf(20));

        ecriture.getListLigneEcriture().clear();
        ecriture.setLibelle("Ecriture avec credit null - Crébit 32.65");
        ecriture.getListLigneEcriture().add(this.createLigne(1, null, "11.65"));
        ecriture.getListLigneEcriture().add(this.createLigne(1, null, null));
        ecriture.getListLigneEcriture().add(this.createLigne(1, null, "21"));

        Assert.assertEquals(ecriture.toString(), ecriture.getTotalCredit(), BigDecimal.valueOf(32.65));
        Assert.assertNotEquals(ecriture.toString(), ecriture.getTotalCredit(), BigDecimal.valueOf(21));
    }
}
