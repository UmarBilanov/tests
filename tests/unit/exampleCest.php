<?php


class exampleCest
{
    
    // tests
    public function tryToTest(UnitTester $I)
    {
        $I->amOnPage('/users');
        $I->seeResponseContainsJson(array('name' => 'john'));
        // response {user: john, profile: { email: john@gmail.com }}
        $I->seeResponseContainsJson(array('email' => 'john@gmail.com'));

    }

    
}
