<?php


class messageCest
{
    // tests
    public function tryToTest(FunctionalTester $I)
    {
        $I->amOnPage('/');
        $I->click('nav-btn__wrapper col-md-2');
        $I->fillField('modal-name', 'Miles');
        $I->fillField('modal-email', 'Miles@miles.com');
        $I->fillField('modal-phone', '0996555555555');
        $I->fillField('modal-textarea', 'Hello dear my friend.what are doing man?');
        $I->click('modal-btn btn-disable');
        $I->see('Hello, Miles', 'h1');
        // $I->seeEmailIsSent(); // only for Symfony2
    }
}
