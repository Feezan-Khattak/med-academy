CREATE DATABASE IF NOT EXISTS medacademys;

\c medacademys;

--Add admin user
INSERT INTO med_users (
                    id,
                    created_at,
                    updated_at,
                    email,
                    password,
                    first_name,
                    last_name,
                    user_id,
                    is_enabled,
                    role
         ) VALUES (
                    1,
                    NOW(),
                    NOW(),
                    'feezanktk2208@gmail.com',
                    '$2a$10$xsnE6hE.W.KnTr.T5RwUNOPuiGLTIcVaZena03wCG2lEi/k4RVYwW',
                    'Super',
                    'Admin',
                    '627623',
                    true,
                    'ADMIN'
        );

--add Email Templates
INSERT INTO template (
    created_at,
    updated_at,
    uuid,
    template_key,
    subject,
    template
) VALUES (
    '2024-06-23 16:28:31.612',
    '2024-06-23 16:28:31.612',
    '647823',
    'FORGOT_PASSWORD',
    'Password Reset Request',
    '<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
    <meta name="description" content="Reset Password">
    <style type="text/css">
        a:hover {
            text-decoration: underline !important;
        }

        @media screen and (max-width: 600px) {
            table {
                width: 100% !important;
            }

            td {
                display: block;
                width: 100%;
                box-sizing: border-box;
                padding: 10px;
            }
        }
    </style>
</head>
<body marginheight="0" topmargin="0" marginwidth="0" style="margin: 0px; background-color: #f2f3f8;" leftmargin="0">
    <table cellspacing="0" border="0" cellpadding="0" width="100%" bgcolor="#f2f3f8"
        style="font-family: ''Open Sans'', sans-serif;">
        <tr>
            <td>
                <table style="background-color: #f2f3f8; max-width:670px;  margin:0 auto;" width="100%" border="0"
                    align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td style="height:20px;">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0"
                                style="max-width:670px;background:#fff; border-radius:3px; text-align:center;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);">
                                <tr>
                                    <td style="height:20px;">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td style="padding:0 35px;">
                                        <h1 style="color:#1e1e2d; font-weight:500; margin:0;font-size:28px;font-family:''Rubik'',sans-serif;">You have
                                            requested to reset your password</h1>
                                        <span
                                            style="display:inline-block; vertical-align:middle; margin:20px 0 18px; border-bottom:1px solid #cecece; width:100px;"></span>
                                        <p style="color:#455056; font-size:14px;line-height:22px; margin:0;">
                                            A unique link to reset your password has been generated for you. To reset your password, click the
                                            button below and follow the instructions.
                                        </p>
                                        <a href="{{password_reset_link}}"
                                            style="background:#20e277;text-decoration:none !important; font-weight:500; margin-top:30px; color:#fff;text-transform:uppercase; font-size:14px;padding:10px 20px;display:inline-block;border-radius:50px;">Reset
                                            Password</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="height:20px;">&nbsp;</td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td style="height:20px;">&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</body>
</html>'
);

--Add subscription plans

