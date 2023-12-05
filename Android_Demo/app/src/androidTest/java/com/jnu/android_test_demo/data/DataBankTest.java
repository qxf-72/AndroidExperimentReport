package com.jnu.android_test_demo.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.jnu.android_test_demo.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DataBankTest {
    DataBank dataSaverBackup;
    ArrayList<BookItem> bookItemsBackup;

    @Before
    public void setUp() throws Exception {
        dataSaverBackup = new DataBank();
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        bookItemsBackup = dataSaverBackup.LoadBookItem(targetContext);
        dataSaverBackup.SaveBookItem(targetContext, new ArrayList<>());
    }

    @After
    public void tearDown() throws Exception {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataSaverBackup.SaveBookItem(targetContext, bookItemsBackup);
    }

    @Test
    public void testLoadBookItem() {
        DataBank dataBank = new DataBank();
        ArrayList<BookItem> bookItems = dataBank.LoadBookItem(
                InstrumentationRegistry.getInstrumentation().getTargetContext()
        );
        Assert.assertEquals(0, bookItems.size());
    }

    @Test
    public void testSaveBookItem() {
        DataBank dataSaver = new DataBank();
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<BookItem> bookItems = new ArrayList<>();
        BookItem bookItem = new BookItem("测试1", R.drawable.book_no_name);
        bookItems.add(bookItem);
        bookItem = new BookItem("测试2", R.drawable.book_no_name);
        bookItems.add(bookItem);

        dataSaver.SaveBookItem(targetContext, bookItems);

        DataBank dataLoader = new DataBank();
        ArrayList<BookItem> bookItemsRead = dataLoader.LoadBookItem(targetContext);
        assertNotSame(bookItems, bookItemsRead);
        assertEquals(bookItems.size(), bookItemsRead.size());
        for (int index = 0; index < bookItems.size(); ++index) {
            assertNotSame(bookItems.get(index), bookItemsRead.get(index));
            assertEquals(bookItems.get(index).getTitle(), bookItemsRead.get(index).getTitle());
            assertEquals(bookItems.get(index).getCoverResourceId(), bookItemsRead.get(index).getCoverResourceId());
        }

    }
}