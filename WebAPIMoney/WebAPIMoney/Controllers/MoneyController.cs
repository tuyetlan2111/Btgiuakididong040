using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using MoneyDataAccess;

namespace WebAPIMoney.Controllers
{
    public class MoneyController : ApiController
    {
        public IEnumerable<money_logs> Get()
        {
            using (MoneyLoverDbEntities entities = new MoneyLoverDbEntities())
            {
                return entities.money_logs.ToList();
            }


        }

        public HttpResponseMessage Get(int id)
        {
            using (MoneyLoverDbEntities entities = new MoneyLoverDbEntities())
            {
                var entiti = entities.money_logs.FirstOrDefault(e => e.id == id);
                if (entiti != null)
                {
                    return Request.CreateResponse(HttpStatusCode.OK, entiti);
                }
                else
                {
                    return Request.CreateErrorResponse(HttpStatusCode.NotFound, "MoneyLover id = " + id.ToString() + " not found");
                }
            }


        }

        public HttpResponseMessage Post([FromBody]money_logs money_Logs)
        {

            using (MoneyLoverDbEntities entities = new MoneyLoverDbEntities())

            {
                try
                {
                    entities.money_logs.Add(money_Logs);
                    entities.SaveChanges();

                    var message = Request.CreateResponse(HttpStatusCode.Created, money_Logs);
                    message.Headers.Location = new Uri(Request.RequestUri + money_Logs.id.ToString());
                    return message;
                }
                catch (Exception e)
                {
                    return Request.CreateErrorResponse(HttpStatusCode.BadRequest, e);
                }
            }

        }

        public HttpResponseMessage Put(int id,[FromBody]money_logs money_Logs)
        {
            try
            {
                using (MoneyLoverDbEntities entities = new MoneyLoverDbEntities())
                {
                    var entiti = entities.money_logs.FirstOrDefault(e => e.id == id);
                    if (entiti == null)
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "MoneyLover id = " + id.ToString() + " not found");

                    }
                    else
                    {
                        entiti.loai = money_Logs.loai;
                        entiti.noi_dung = money_Logs.noi_dung;
                        entiti.so_tien = money_Logs.so_tien;
                        entiti.ngay = money_Logs.ngay;

                        entities.SaveChanges();
                        return Request.CreateResponse(HttpStatusCode.OK, entiti);
                    }
                }
        
            }
            catch (Exception exc)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, exc);
            }
           
        }
        

        public HttpResponseMessage Delete(int id)
        {
            using (MoneyLoverDbEntities entities = new MoneyLoverDbEntities())
            {
                try
                {
                    var entiti = entities.money_logs.Remove(entities.money_logs.FirstOrDefault(e => e.id == id));
                    if (entiti == null)
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "MoneyLover id = " + id.ToString() + " not found");
                    }
                    else
                    {
                        entities.money_logs.Remove(entiti);
                        entities.SaveChanges();
                        return Request.CreateResponse(HttpStatusCode.OK);
                    }


                }
                catch (Exception exc)
                {
                    return Request.CreateErrorResponse(HttpStatusCode.BadRequest, exc);
                }
            }
        }


    }






}
