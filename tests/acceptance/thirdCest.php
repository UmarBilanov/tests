<?php


class thirdCest
{
    // tests
    public function loginUnSuccessfully(AcceptanceTester $I)
    {
        $I->amOnPage('https://askartec.com/');
        $I->see('О нас');
        // $I->fillField('email', '0773426466');
        // $I->fillField('pass', 'wrongpass');
        // $I->click('loginbutton');
        // $I->see('The password that you\'ve entered is incorrect');
        // // $I->seeLink('https://www.facebook.com/recover/initiate?lwv=120&lwc=1348092&ars=facebook_login_pw_error');
        // $I->seeLink('Recover Your Account');
        // $I->seeResponseCodeIs(203);
        // $I->see('');
    }

    public function pageNotFound(AcceptanceTester $I)
    {
        $I->amOnPage('/welcome');
        $I->see('Страница не найдена');
    }
}
