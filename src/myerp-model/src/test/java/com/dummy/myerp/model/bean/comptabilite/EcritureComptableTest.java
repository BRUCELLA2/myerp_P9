package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;

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
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Débit 10.55");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "6.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "4.05", null));
        Assert.assertTrue(vEcriture.toString(), vEcriture.getTotalDebit().equals(BigDecimal.valueOf(10.55)));
        Assert.assertFalse(vEcriture.toString(), vEcriture.getTotalDebit().equals(BigDecimal.valueOf(11)));

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Ecriture avec debit null - Débit 12.65");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "2.65", null));
        Assert.assertTrue(vEcriture.toString(), vEcriture.getTotalDebit().equals(BigDecimal.valueOf(12.65)));
        Assert.assertFalse(vEcriture.toString(), vEcriture.getTotalDebit().equals(BigDecimal.valueOf(13)));
    }

    @Test
    public void getTotalCredit() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Crédit 20.75");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, "10"));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, "10.75"));
        Assert.assertTrue(vEcriture.toString(), vEcriture.getTotalCredit().equals(BigDecimal.valueOf(20.75)));
        Assert.assertFalse(vEcriture.toString(), vEcriture.getTotalCredit().equals(BigDecimal.valueOf(20)));

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Ecriture avec credit null - Crébit 32.65");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, "11.65"));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, "21"));
        Assert.assertTrue(vEcriture.toString(), vEcriture.getTotalCredit().equals(BigDecimal.valueOf(32.65)));
        Assert.assertFalse(vEcriture.toString(), vEcriture.getTotalCredit().equals(BigDecimal.valueOf(21)));
    }
}
