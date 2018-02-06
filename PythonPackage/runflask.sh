DATATIME=$(date +%Y%m%d)
LOGSTART="/opt/logs/error/error"
LOGEND=".log"
LOGNAME=$LOGSTART$DATATIME$LOGEND
start()
{
  echo $"starting run uwsgi....."
  echo LOGNAME
  if [ ! -f "$LOGNAME" ]; then
    touch "$LOGNAME"
  fi
  uwsgi /opt/PythonMyTeam/uwsgiconfig.ini -d ${LOGNAME} -p 8 --threads 10
  echo "start ok "
}

case "$1" in
start)
  start
  ;;
*)
  echo $"Usage: $0{start}"
  exit 1
  ;;
esac
exit
