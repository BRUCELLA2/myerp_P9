package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JournalComptableTest {

    @Test
    public void getByCode() {

        JournalComptable journalComptable = new JournalComptable("FF", "journal test 1");
        JournalComptable journalComptable2 = new JournalComptable("GG", "journal test 2");
        List<JournalComptable> list = new ArrayList<>();
        list.add(journalComptable);
        list.add(journalComptable2);

        Assert.assertTrue(journalComptable.toString(), JournalComptable.getByCode(list, "FF").getLibelle().equals("journal test 1"));
        Assert.assertFalse(journalComptable2.toString(), JournalComptable.getByCode(list, "GG").getLibelle().equals("journal test 1"));
        Assert.assertTrue("journal inexistant", JournalComptable.getByCode(list, "AA") == null);
    }
}