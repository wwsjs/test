import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '工号/学号',
    align:"center",
    dataIndex: 'workNo'
   },
   {
    title: '姓名',
    align:"center",
    dataIndex: 'username'
   },
   {
    title: '联系电话',
    align:"center",
    dataIndex: 'phone'
   },
   {
    title: '邮箱',
    align:"center",
    dataIndex: 'email'
   },
   {
    title: '办公室/实验室',
    align:"center",
    dataIndex: 'roomNo'
   },
   {
    title: '人员类别',
    align:"center",
    dataIndex: 'personType_dictText'
   },
   {
    title: '年级',
    align:"center",
    dataIndex: 'grade'
   },
   {
    title: '课题组编号',
    align:"center",
    dataIndex: 'groupId_dictText'
   },
   {
    title: '管理员/组长/用户',
    align:"center",
    dataIndex: 'roleCode_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '工号/学号',
    field: 'workNo',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入工号/学号!'},
          ];
     },
  },
  {
    label: '姓名',
    field: 'username',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入姓名!'},
          ];
     },
  },
  {
    label: '联系电话',
    field: 'phone',
    component: 'Input',
  },
  {
    label: '邮箱',
    field: 'email',
    component: 'Input',
  },
  {
    label: '办公室/实验室',
    field: 'roomNo',
    component: 'Input',
  },
  {
    label: '人员类别',
    field: 'personType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"person_type",
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入人员类别!'},
          ];
     },
  },
  {
    label: '年级',
    field: 'grade',
    component: 'Input',
  },
  {
    label: '课题组编号',
    field: 'groupId',
    component: 'JSearchSelect',
    componentProps:{
       dict:"lab_group,mentor,group_id"
    },
  },
  {
    label: '管理员/组长/用户',
    field: 'roleCode',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"lab_role_code",
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入管理员/组长/用户!'},
          ];
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  workNo: {title: '工号/学号',order: 0,view: 'text', type: 'string',},
  username: {title: '姓名',order: 1,view: 'text', type: 'string',},
  phone: {title: '联系电话',order: 2,view: 'text', type: 'string',},
  email: {title: '邮箱',order: 3,view: 'text', type: 'string',},
  roomNo: {title: '办公室/实验室',order: 4,view: 'text', type: 'string',},
  personType: {title: '人员类别',order: 5,view: 'list', type: 'string',dictCode: 'person_type',},
  grade: {title: '年级',order: 6,view: 'text', type: 'string',},
  groupId: {title: '课题组编号',order: 7,view: 'sel_search', type: 'string',dictTable: "lab_group", dictCode: 'group_id', dictText: 'mentor',},
  roleCode: {title: '管理员/组长/用户',order: 8,view: 'list', type: 'string',dictCode: 'lab_role_code',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}