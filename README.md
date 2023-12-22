1. Download
2. Setup with IDE and Maven as build tool
3. edit three settings in application.yaml file if needed:
  - wakanda_local_folder_path_wakanda - path to folder where wakanda will save csv files (looks for the newest file in this folder parsing date from file name 'WK_payouts_yyyymmdd_hhmmss.csv')
  - wakanda_post_url - url to post csv file
  - wakanda_upload_cron_expression - cron expression for scheduler
4. run application
5. check logs for errors
6. for end-to-end testing use api method accessible at /api/v1/initupload endpoint like this: http://localhost:8080/api/v1/initupload (POST method, no added body, no added headers)
