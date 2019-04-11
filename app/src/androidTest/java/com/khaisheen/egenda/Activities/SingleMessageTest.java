package com.khaisheen.egenda.Activities;

import com.google.firebase.Timestamp;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SingleMessageTest {

    @Test
    public void testSingleMessage(){
        SingleMessage mMessage = new SingleMessage(null, null, null);
        assertNull(mMessage.getSentBy());
        assertNull(mMessage.getTimestamp());
        assertNull(mMessage.getMessage());
    }

    @Test
    public void getMessageTest() {
        String someText = "Hey!";
        SingleMessage mMessage = new SingleMessage();

        mMessage.setMessage(someText);
        assertEquals(mMessage.getMessage(),someText);

    }

    @Test
    public void getSentByTest() {
        String someUser= "Booga";
        SingleMessage mMessage = new SingleMessage();

        mMessage.setSentBy(someUser);
        assertEquals(mMessage.getSentBy(),someUser);
    }

    @Test
    public void getTimestamp() {
        Timestamp mTimestamp = Timestamp.now();
        SingleMessage mMessage = new SingleMessage();

        mMessage.setTimestamp(mTimestamp);
        assertEquals(mMessage.getTimestamp(),mTimestamp);

    }

    @Test
    public void getTimeTest() {

        Timestamp mTimestamp = Timestamp.now();
        SingleMessage mMessage = new SingleMessage();

        mMessage.setTimestamp(mTimestamp);
        Date mDate = mTimestamp.toDate();
        String mTime = new SimpleDateFormat("dd/MM, HH:mm").format(mDate);

        assertEquals(mMessage.getTime(),mTime);
    }

    /*@Test
    public void setTimestampTest() {
        String expResult = "1970-01-01T00:00:00Z";

        SingleMessage mMessage = new SingleMessage();
        mMessage.setTimestamp(new Timestamp(new Date(1970,01,01,0,0,0)));

    }*/

    @Test
    public void setMessageTest() {
        String expRes = "I'm tired";

        SingleMessage mMessage = new SingleMessage();
        mMessage.setMessage("I'm tired");

        assertEquals(mMessage.getMessage(), expRes);
    }


    @Test
    public void setSentByTest() {

        String expRes = "Booga";

        SingleMessage mMessage = new SingleMessage();
        mMessage.setSentBy("Booga");

        assertEquals(mMessage.getSentBy(), expRes);
    }
}