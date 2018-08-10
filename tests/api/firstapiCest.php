<?php


class firstapiCest
{
    // tests
    // public function seeResponseIsHtml()
    // {
    //     .then(response => response.json())
    //     .then(json => console.log(json))
    // }

    public function tryToTest(ApiTester $I)
    {
        $I->sendGET('/todos/1');
        // $I->seeResponseCodeIs(HttpCode::OK); // 200
        $I->seeResponseIsJson();
        $I->seeResponseMatchesJsonType([
            'userId' => 'integer',
            'id' => 'integer',
            'title' => 'string',
            'completed' => 'boolean'
            ]);
    }

    public function testToContains(ApiTester $I)
    {
        $I->sendGET('/users');
        $I->seeResponseContainsJson(array('name' => 'Leanne Graham'));
        $I->seeResponseContainsJson(array('username' => 'Antonette'));
        $I->sendGET('/users/3');
        $I->seeResponseContainsJson(array('phone' => '1-463-123-4447'));
        $I->sendGET('/albums?userId=1');
        $I->seeResponseCodeIs(200);
    }
}