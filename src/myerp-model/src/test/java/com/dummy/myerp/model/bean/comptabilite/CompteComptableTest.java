package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class CompteComptableTest {

    @Test
    public void getByNumero() {
        CompteComptable compteComptable = new CompteComptable(3, "compte test");
        CompteComptable compteComptable2 = new CompteComptable(2, "compte test 2");
        List<CompteComptable> listCompte = new ArrayList<>();
        listCompte.add(compteComptable);
        listCompte.add(compteComptable2);

        Assert.assertTrue(compteComptable.toString(), CompteComptable.getByNumero(listCompte, 3).getLibelle().equals("compte test"));
        Assert.assertFalse(compteComptable.toString(), CompteComptable.getByNumero(listCompte, 2).getLibelle().equals("compte test"));
        Assert.assertTrue(compteComptable.toString(), CompteComptable.getByNumero(listCompte, 4) == null);
    }

}