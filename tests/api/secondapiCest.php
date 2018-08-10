<?php


class secondapiCest
{


    // tests
    public function tryToTest(ApiTester $I)
    {
        // $I->amAWSAuthenticated();
        // $I->sendPOST('/', ['subject' => 'Read this!', 'to' => 'johndoe@example.com']);
        // $I->sendPOST('/', array(
        //     'modal-name' => 'User',
        //     'modal-email' => 'subscriber1@test.eml',
        //     'modal-phone' => '0996555555555',
        //     'modal-textarea' => 'Hello dear my friend.what are doing man?',
        // ));
        $I->sendGET('https://askartec.com');
        // $I->amOnPage('https://askartec.com');
        $I->seeResponseContains('Написать нам');
        // $I->see('Написать нам');

        $I->sendPOST('#Написать нам',
            ['attributes' => '{"modal-name":"User","modal-email":"subscriber1@test.eml","modal-phone":0996555555555,"modal-textarea":"Hello dear my friend.what are doing man?"}']
            );
        // $I->seeResponseCodeIs(202);
        $I->seeResponseCodeIs(HttpCode::OK); // 200
    }
}