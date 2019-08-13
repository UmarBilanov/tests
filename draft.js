<script>
import Vue from 'vue';
import { Platform } from 'quasar';
import SalePoint from '../components/SalePoints'
import Staff from '../components/Staff'

function getFileSuccess(fileEntry){
	fileEntry.file(
		readFile, // success
		function(err){ // failure
			console.log('Failed to get file.',err);
		}
	);
}

function readFile(file){
	console.log('got file...',JSON.stringify(file));
	var reader = new window.FileReader();
	reader.oneerror = function(e){
		console.log('FileReader Error: ',e.target.result);
	};
	reader.onloadend = function(fileObject) {
		console.log('we have the file:',JSON.stringify(fileObject));
		console.log('the image data is in fileObject.target._result');
	};
	reader.readAsDataURL(file);
}

export default {
    name: 'rpSettings',

    components: { SalePoint, Staff },

    data() {
        return {
            headerTitle: 'Tölegler',
            userConf: this.$config.user,
            avatarError: null,
            input_name: this.$config.user.userCustomName,
            userType: this.$config.user.userType,
            isIndividual: false,
            isCashier: false,
            isLegalEntity: false,
            individualToggle: false,
            touchId: this.$config.session.faceTouchIdEnabled,
            widgetDarkStyle: this.$config.user.widgetDarkStyle,
            widgetBalanceShow: this.$config.user.widgetShowBalance,
            // checkedRadioBtn: null,
        }
    },

    computed: {
    },

    methods: {
        uploadUserImage() {

            if (!this.$q.platform.is.mobile || !navigator || !navigator.camera) {
                var errMsg = "Плагин загрузки изображения поддерживается только на мобильных устройствах";
                this.avatarError = errMsg;
                this.$log.error(errMsg);
                return;
            }

            // https://cordova.apache.org/docs/en/latest/reference/cordova-plugin-camera/index.html

            let options = {
                destinationType : Camera.DestinationType.FILE_URI,
                sourceType : Camera.PictureSourceType.SAVEDPHOTOALBUM,
	            encodingType: Camera.EncodingType.JPEG,
	            mediaType: Camera.MediaType.PICTURE
            }

            var _this = this;
            navigator.camera.getPicture(function(data) {
                // success
	            _this.$log.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + data);

	            let filename = data.substring(data.lastIndexOf('/')+1);
	            let path =  data.substring(0,data.lastIndexOf('/')+1);
	            _this.$log.warn(filename + " " + path);
	            //then use the method reasDataURL  btw. var_picture is ur image variable
	            // window.file.readAsDataURL(path, filename).then(res=> userConf.userIcon = res  );
	            window.resolveLocalFileSystemURL(data, function (file) {
		                    _this.$log.warn("11111111111111111111111111111111111" + file.fullPath);
		                    _this.$log.warn("11111111111111111111111111111111111" + JSON.stringify(file));
		                    // _this.userConf.userIcon = file.fullPath;
		            getFileSuccess(file);
		                }, function (err) {
		                    // Файл не найден
		                    _this.$log.warn("22222222222222222222222222222222222" + JSON.stringify(err));
		                });
                _this.$rpapi.images.uploadUser(data, function(err, data) {
                    if (err) {
                        var errMsg = "Ошибка при загрузке изображения";
                        _this.avatarError = errMsg;
                        _this.$log.error(errMsg + ": " + JSON.stringify(err));
                    }
                    _this.$log.warn("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + data);
                });
            }, function(err) {
                // error
                var errMsg = "Функция загрузки изображения вернула ошибку";
                _this.avatarError = errMsg;
                _this.$log.error(errMsg, err);
                return;
            }, options);
        },

        changeName: async function (event) {
            event.preventDefault();
            var _this = this;
            var userName = this.input_name;

            if (userName) {
                _this.$rpapi.user.update(userName, function(err, data) {
                    if (err) {
                        var errMsg = "Ошибка при изменении имени пользователя";
                        _this.avatarError = errMsg;
                        _this.$log.error(errMsg + ": " + JSON.stringify(err));
                    }
                });
            }
        },
        turnTouchIdOn: function () {
            var sess = this.$config.session;
            sess.faceTouchIdEnabled = this.touchId;
            sess.save();
        },
        widgetDarkstyle: function () {
            var userconf = this.$config.user;
            userconf.widgetDarkStyle = this.widgetDarkStyle;
            userconf.save();
        },
        widgetbalanceShow: function () {
            var userconf = this.$config.user;
            userconf.widgetShowBalance = this.widgetBalanceShow;
            userconf.save();
        },
        // checkedRadio: function () {
        //     this.$config.user.userSelectedType = this.checkedRadioBtn;
        // }
    },

    created() {

    },

    mounted() {
        this.checkedRadioBtn = this.$config.user.userSelectedType;

        // Если сессия инвалидна, переходим на экран логина
        if (!this.$config.session.isSessionValid) {
            this.$router.replace({'path': '/'});
            return;
        }

        // Если сессия валидна, но приложение залочено, переходим на экран ввода пинкода
        if (this.$config.session.isAppLocked) {
            this.$router.replace({'path': '/enter'});
            return;
        }
        this.$log.debug("Запущен экран изменения настроек приложения");
    }
}



</script>

<style scoped>
  .Error-text-style {
    box-sizing: border-box;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    width: 90%;
    outline: none;
    display: block;
    border: none;
    background: transparent;
    font: 18px Arial, Inter, sans-serif;
    color: red;
    text-align: center;
  }
  .q-field--with-bottom {
    padding-bottom: 0px;
  }
</style>
