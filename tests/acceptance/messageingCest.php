<?php


class messageingCest
{

    // tests
    public function messageingSuccess(AcceptanceTester $I)
    {
        $I->amOnPage('/landing');
        $I->submitForm('#message_form', array('message' => array(
            'name' => 'Obama',
            'email' => 'test@test.com',
            'message' => 'test message'
            )), 'submitButton');
        $I->see('Ваше сообщение успешно отправлено!');
    }
}