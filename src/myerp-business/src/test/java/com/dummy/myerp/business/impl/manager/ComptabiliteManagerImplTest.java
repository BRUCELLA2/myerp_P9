package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void validateSettersAndGetters() {
        PojoClass comptabiliteManagerImpl = PojoClassFactory.getPojoClass(ComptabiliteManagerImpl.class);

        Validator validator = ValidatorBuilder.create()
        .with(new SetterTester(), new GetterTester())
        .build();
        validator.validate(comptabiliteManagerImpl);
    }

    /**
     * Vérification RG5.
     * Test passant.
     *
     * @throws Exception
     */
    @Test
    public void addReferenceTest() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(1);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));

        SequenceEcritureComptable vSequence = new SequenceEcritureComptable("AC", 2018, 5);

        DaoProxy daoProxy = Mockito.mock(DaoProxy.class);
        ComptabiliteDao comptabiliteDao = Mockito.mock(ComptabiliteDao.class);
        TransactionManager transactionManager = Mockito.mock(TransactionManager.class);

        Mockito.when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
        Mockito.when(comptabiliteDao.getSequenceEcritureComptable("AC", 2018)).thenReturn(vSequence);

        manager.configure(null, daoProxy, transactionManager);
        manager.addReference(vEcritureComptable);
        Assert.assertEquals(vEcritureComptable.toString(), "AC-2018/00006", vEcritureComptable.getReference());
    }

    /**
     * Vérification RG5.
     * Sequence non trouvée, initialisation de la séquence
     *
     * @throws Exception
     */
    @Test
    public void addReferenceWithoutSequenceTest() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(1);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));

        DaoProxy daoProxy = Mockito.mock(DaoProxy.class);
        ComptabiliteDao comptabiliteDao = Mockito.mock(ComptabiliteDao.class);
        TransactionManager transactionManager = Mockito.mock(TransactionManager.class);

        Mockito.when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
        Mockito.doThrow(NotFoundException.class).when(comptabiliteDao).getSequenceEcritureComptable("AC", 2018);

        AbstractBusinessManager.configure(null, daoProxy, transactionManager);
        manager.addReference(vEcritureComptable);
        Assert.assertEquals(vEcritureComptable.toString(), "AC-2018/00001", vEcritureComptable.getReference());
    }

    /**
     * Test passant.
     *
     * @throws Exception
     */
    @Test
    public void checkEcritureComptableTest() throws Exception {

        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(1);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));

        manager.checkEcritureComptable(vEcritureComptable);

    }

    /**
     * Vérification RG2,RG3, RG6.
     * Test passant.
     *
     * @throws Exception
     */
    @Test
    public void checkEcritureComptableUnit() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(1);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Test avec une écriture comptable dont les données sont incorrectes.
     * Doit lever une exception FunctionalException.
     *
     * @throws Exception
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Vérification de la RG2.
     * Test avec une écriture comptable non équilibrée.
     * Doit lever une exception FunctionalException.
     *
     * @throws Exception
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(1234)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Vérification RG3.
     * Test avec une écriture comptable contenant deux lignes de débits et aucune en crédit.
     * Doit lever une exception FunctionalException.
     *
     * @throws Exception
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnit2DebitLine() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Vérification RG3.
     * Test avec une écriture comptable contenant deux lignes de crédits et aucune de débit.
     * Doit lever une exception FunctionalException.
     *
     * @throws Exception
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnit2CreditLine() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, null, new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, null, new BigDecimal(123)));

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Vérification RG3.
     * Test avec une écriture comptable contenant qu'une seule ligne de crédit.
     * Doit lever une exception FunctionalException.
     *
     * @throws Exception
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitOneLineCredit() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, null, new BigDecimal(123)));

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Vérification RG3.
     * Test avec une écriture comptable contenant qu'une seule ligne de débit.
     * Doit lever une exception FunctionalException.
     *
     * @throws Exception
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitOneLineDebit() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(1);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),null));

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Vérification RG5.
     * Test avec une écriture comptable contenant une référence ayant le mauvais code journal.
     * Doit lever une exception FunctionalException.
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitBadCodeJournal() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(1);
        vEcritureComptable.setJournal(new JournalComptable("RG", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Vérification RG5.
     * Test avec une écriture comptable contenant une référence ayant la mauvaise année.
     * Doit lever une exception FunctionalException.
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitBadYear() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(1);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2016/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Vérification RG5.
     * Test avec une écriture comptable contenant un numéro de plus de 5 chiffres.
     * Doit lever une exception FunctionalException.
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitBadNumber() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(1);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2018/0000123");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }


    /**
     * Vérification RG6.
     * Test passant.
     *
     * @throws Exception
     */
    @Test
    public void checkEcritureComptableContext() throws Exception {

        EcritureComptable ecritureComptable;
        ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(1);
        ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        ecritureComptable.setDate(new Date());
        ecritureComptable.setReference("AC-2018/00001");
        ecritureComptable.setLibelle("Libellé");
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                null, new BigDecimal(123),
                                                                                null));
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                null, null,
                                                                                new BigDecimal(123)));

        DaoProxy daoProxy = Mockito.mock(DaoProxy.class);
        ComptabiliteDao comptabiliteDao = Mockito.mock(ComptabiliteDao.class);

        Mockito.when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
        Mockito.when(comptabiliteDao.getEcritureComptableByRef("AC-2018/00001")).thenReturn(ecritureComptable);

        AbstractBusinessManager.configure(null, daoProxy, null);
        manager.checkEcritureComptableContext(ecritureComptable);
    }

    /**
     * Vérification RG6.
     * Test avec une écriture dont la référence existe déjà.
     * Doit lever une exception FunctionalException.
     *
     * @throws Exception
     */
    @Test (expected = FunctionalException.class)
    public void checkEcritureComptableContextDouble() throws Exception {

        EcritureComptable ecritureComptable;
        ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(1);
        ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("Libellé");
        ecritureComptable.setReference("AC-2018/00001");
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));

        EcritureComptable ecritureComptable2;
        ecritureComptable2 = new EcritureComptable();
        ecritureComptable2.setId(2);
        ecritureComptable2.setJournal(new JournalComptable("AC", "Achat"));
        ecritureComptable2.setDate(new Date());
        ecritureComptable2.setLibelle("Libellé");
        ecritureComptable2.setReference("AC-2018/00001");
        ecritureComptable2.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
        ecritureComptable2.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));

        DaoProxy daoProxy = Mockito.mock(DaoProxy.class);
        ComptabiliteDao comptabiliteDao = Mockito.mock(ComptabiliteDao.class);

        Mockito.when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
        Mockito.when(comptabiliteDao.getEcritureComptableByRef("AC-2018/00001")).thenReturn(ecritureComptable);

        AbstractBusinessManager.configure(null, daoProxy, null);
        manager.checkEcritureComptableContext(ecritureComptable2);
    }

    /**
     * Vérification RG6.
     * Test avec une nouvelle écriture (qui n'a pas d'id) et dont la référence n'existe pas dans une autre écriture.
     * Test passant.
     *
     * @throws Exception
     */
    @Test
    public void checkEcritureComptableContextNewECNotFound() throws Exception{

        EcritureComptable ecritureComptable;
        ecritureComptable = new EcritureComptable();
        ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        ecritureComptable.setDate(new Date());
        ecritureComptable.setReference("AC-2018/00001");
        ecritureComptable.setLibelle("Libellé");
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                            null, new BigDecimal(123),
                                            null));
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                            null, null,
                                                    new BigDecimal(123)));

        DaoProxy daoProxy = Mockito.mock(DaoProxy.class);
        ComptabiliteDao comptabiliteDao = Mockito.mock(ComptabiliteDao.class);

        Mockito.when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
        Mockito.doThrow(NotFoundException.class).when(comptabiliteDao).getEcritureComptableByRef("AC-2018/00001");

        AbstractBusinessManager.configure(null, daoProxy, null);
        manager.checkEcritureComptableContext(ecritureComptable);

    }

    /**
     * Vérification RG6.
     * Test avec une nouvelle écriture (qui n'a pas d'id) et dont la référence existe déjà.
     * Doit lever une exception FunctionalException.
     *
     * @throws Exception
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableContextNewECFound() throws Exception {

        EcritureComptable ecritureComptable;
        ecritureComptable = new EcritureComptable();
        ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("Libellé");
        ecritureComptable.setReference("AC-2018/00001");
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));

        EcritureComptable ecritureComptable2;
        ecritureComptable2 = new EcritureComptable();
        ecritureComptable2.setId(1);
        ecritureComptable2.setJournal(new JournalComptable("AC", "Achat"));
        ecritureComptable2.setDate(new Date());
        ecritureComptable2.setLibelle("Libellé");
        ecritureComptable2.setReference("AC-2018/00002");
        ecritureComptable2.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
        ecritureComptable2.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));

        DaoProxy daoProxy = Mockito.mock(DaoProxy.class);
        ComptabiliteDao comptabiliteDao = Mockito.mock(ComptabiliteDao.class);

        Mockito.when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
        Mockito.when(comptabiliteDao.getEcritureComptableByRef("AC-2018/00001")).thenReturn(ecritureComptable2);

        AbstractBusinessManager.configure(null, daoProxy, null);
        manager.checkEcritureComptableContext(ecritureComptable);
    }
}
