<?php


class secondCest
{
    public function tryToTest(AcceptanceTester $I)
    {
        $I->amOnPage('/');
        $I->wantTo('test my page');
        $I->see('hi what are doing');

    }
}
